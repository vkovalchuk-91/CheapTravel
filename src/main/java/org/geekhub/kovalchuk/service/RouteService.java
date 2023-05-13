package org.geekhub.kovalchuk.service;

import org.geekhub.kovalchuk.json.JsonParser;
import org.geekhub.kovalchuk.json.JsonRequestMaker;
import org.geekhub.kovalchuk.json.entity.MonthPriceJsonResponse;
import org.geekhub.kovalchuk.model.entity.CityInOperation;
import org.geekhub.kovalchuk.model.entity.Location;
import org.geekhub.kovalchuk.model.entity.Route;
import org.geekhub.kovalchuk.repository.TaskQueueRepository;
import org.geekhub.kovalchuk.repository.jpa.CityInOperationRepository;
import org.geekhub.kovalchuk.repository.jpa.RouteRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RouteService {
    private final RouteRepository routeRepository;
    private final CityInOperationRepository cityRepository;
    private final TaskQueueRepository taskQueueRepository;

    public RouteService(RouteRepository routeRepository,
                        CityInOperationRepository cityRepository,
                        TaskQueueRepository taskQueueRepository) {
        this.routeRepository = routeRepository;
        this.cityRepository = cityRepository;
        this.taskQueueRepository = taskQueueRepository;
    }

    public boolean isRouteTableEmpty() {
        return routeRepository.count() == 0;
    }

    public void runRoutesUpdater() {
        List<Route> allRoutes = routeRepository.findAll();
        allRoutes.forEach(route -> route.setActivity(false));
        routeRepository.saveAll(allRoutes);

        List<CityInOperation> cities = cityRepository.findAll();

        int addedTasksCounter = 0;
        for (CityInOperation fromCity : cities) {
            if (fromCity.isActivity()) {
                for (CityInOperation toCity : cities) {
                    if (toCity.isActivity() && !fromCity.equals(toCity)) {
                        Location fromCityLocation = fromCity.getLocation();
                        Location toCityLocation = toCity.getLocation();
                        Route route = routeRepository.findRouteByFromCityAndToCity(fromCityLocation, toCityLocation);
                        if (route == null) {
                            route = new Route();
                            route.setFromCity(fromCityLocation);
                            route.setToCity(toCityLocation);
                        }

                        addRequestTaskToQueue(route);
                        addedTasksCounter++;
                    }
                }
            }
        }
        System.out.println("Routes updater is running, added " + addedTasksCounter + " new tasks!");
    }

    public void setNeedToStartUpdateRoutes() {
        taskQueueRepository.setNeedToStartUpdateRoutes(true);
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
                .map(MonthPriceJsonResponse.Content.Results.Quote::getIsDirect)
                .filter(Boolean::booleanValue)
                .count();

        return numberOfDirectFlights > 0;
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
