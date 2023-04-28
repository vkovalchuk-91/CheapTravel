package org.geekhub.kovalchuk.service;

import org.geekhub.kovalchuk.json.JsonParser;
import org.geekhub.kovalchuk.json.JsonRequestMaker;
import org.geekhub.kovalchuk.json.entity.MonthPriceJsonEntity;
import org.geekhub.kovalchuk.model.entity.CityInOperation;
import org.geekhub.kovalchuk.model.entity.Location;
import org.geekhub.kovalchuk.model.entity.LocationType;
import org.geekhub.kovalchuk.model.entity.Route;
import org.geekhub.kovalchuk.repository.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RouteService {
    public static final String TYPE_CITY = "PLACE_TYPE_CITY";
    RouteRepository routeRepository;
    CityInOperationRepository cityRepository;
    LocationTypeRepository locationTypeRepository;
    LocationRepository locationRepository;
    TaskQueueRepository taskQueueRepository;

    public RouteService(RouteRepository routeRepository,
                        CityInOperationRepository cityRepository,
                        LocationTypeRepository locationTypeRepository,
                        LocationRepository locationRepository,
                        TaskQueueRepository taskQueueRepository) {
        this.routeRepository = routeRepository;
        this.cityRepository = cityRepository;
        this.locationTypeRepository = locationTypeRepository;
        this.locationRepository = locationRepository;
        this.taskQueueRepository = taskQueueRepository;
    }

    public boolean isRouteTableEmpty() {
        return routeRepository.count() == 0;
    }

    public Route getRouteById(long routeId) {
        return routeRepository.findById(routeId).orElse(null);
    }

    public void runRoutesUpdater() {
        List<Route> allRoutes = routeRepository.findAll();
        allRoutes.forEach(route -> route.setActivity(false));

        List<CityInOperation> cities = cityRepository.findAll();

        for (CityInOperation fromCity : cities) {
            if (fromCity.isActivity()) {
                for (CityInOperation toCity : cities) {
                    if (toCity.isActivity() && !fromCity.equals(toCity)) {
                        Location fromCityLocation = fromCity.getLocation();
                        Location toCityLocation = toCity.getLocation();
                        Route route = getRouteByLocations(fromCityLocation, toCityLocation);
                        if (route == null) {
                            route = new Route();
                            route.setFromCity(fromCityLocation);
                            route.setToCity(toCityLocation);
                        }

                        addRequestTaskToQueue(route);
                    }
                }
            }
        }
    }

    public Route getRouteByCitiesNames(String fromCity, String toCity) {
        LocationType locationType = locationTypeRepository.findLocationTypeEntityByName(TYPE_CITY);
        Location fromCityLocation = locationRepository.findLocationByNameAndLocationType(fromCity, locationType);
        Location toCityLocation = locationRepository.findLocationByNameAndLocationType(toCity, locationType);

        return routeRepository.findRouteByFromCityAndToCity(fromCityLocation, toCityLocation);
    }

    public Route getRouteByCitiesIds(long fromCityId, long toCityId) {
        Location fromCityLocation = locationRepository.findLocationByEntityId(fromCityId);
        Location toCityLocation = locationRepository.findLocationByEntityId(toCityId);

        return routeRepository.findRouteByFromCityAndToCity(fromCityLocation, toCityLocation);
    }

    private void addRequestTaskToQueue(Route route) {
        RouteSetHaveFlightAndSaveToDbTask task = new RouteSetHaveFlightAndSaveToDbTask(route);
        taskQueueRepository.getTaskQueue().add(task);
    }

    private boolean hasRouteFlights(Route route) {
        LocalDate date = LocalDate.now().plusMonths(1);
        long fromCityId = route.getFromCity().getEntityId();
        long toCityId = route.getToCity().getEntityId();
        int year = date.getYear();
        int month = date.getMonthValue();

        String monthPricesJson;
        try {
            monthPricesJson = JsonRequestMaker.getMonthPricesJson("USD", fromCityId, toCityId, year, month);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        long numberOfDirectFlights = JsonParser.getMonthPrices(monthPricesJson)
                .getContent().getResults().getQuotes().values().stream()
                .map(MonthPriceJsonEntity.Content.Results.Quote::getIsDirect)
                .filter(Boolean::booleanValue)
                .count();

        return numberOfDirectFlights > 0;
    }

    private Route getRouteByLocations(Location fromCityLocation, Location toCityLocation) {
        return routeRepository.findRouteByFromCityAndToCity(fromCityLocation, toCityLocation);
    }

    private class RouteSetHaveFlightAndSaveToDbTask implements Runnable {
        Route route;

        public RouteSetHaveFlightAndSaveToDbTask(Route route) {
            this.route = route;
        }

        @Override
        public void run() {
            boolean hasRouteFlights = hasRouteFlights(route);
            route.setHaveFlights(hasRouteFlights);
            route.setCheckDate(LocalDateTime.now());
            route.setActivity(true);
            routeRepository.save(route);
        }
    }
}
