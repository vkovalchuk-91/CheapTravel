package org.geekhub.kovalchuk.service;

import org.geekhub.kovalchuk.config.PropertiesConfig;
import org.geekhub.kovalchuk.model.*;
import org.geekhub.kovalchuk.model.entity.*;
import org.geekhub.kovalchuk.model.request.SearchParamsRequest;
import org.geekhub.kovalchuk.repository.CityInOperationRepository;
import org.geekhub.kovalchuk.repository.FlightRepository;
import org.geekhub.kovalchuk.repository.LocationRepository;
import org.geekhub.kovalchuk.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightMatcherService {
    FlightRepository flightRepository;
    CityInOperationRepository cityRepository;
    RouteRepository routeRepository;
    LocationRepository locationRepository;
    CurrencyService currencyService;
    PropertiesConfig properties;
    public static final String TYPE_CITY = "PLACE_TYPE_CITY";

    public FlightMatcherService(FlightRepository flightRepository,
                                CityInOperationRepository cityRepository,
                                RouteRepository routeRepository,
                                LocationRepository locationRepository,
                                CurrencyService currencyService,
                                PropertiesConfig properties) {
        this.flightRepository = flightRepository;
        this.cityRepository = cityRepository;
        this.routeRepository = routeRepository;
        this.locationRepository = locationRepository;
        this.currencyService = currencyService;
        this.properties = properties;
    }

    public List<ManyFlightsUnit> getFlightsByRequestParams(SearchParamsRequest searchParamsRequest) {

        List<ManyFlightsUnit> outputFlightsList;
        Currency currency = currencyService.getCurrencyByCode("USD");

        Location startLocation1 =
                locationRepository.findLocationByEntityId(searchParamsRequest.getStartPointsIds().get(0));
        Location endLocation1 =
                locationRepository.findLocationByEntityId(searchParamsRequest.getEndPointsIds().get(0));
        if (startLocation1.getLocationType().getName().equals(TYPE_CITY)) {
            if (endLocation1.getLocationType().getName().equals(TYPE_CITY)) {
                outputFlightsList = getFirstStepFlightsByStartCityAndNextCity(startLocation1,
                        endLocation1,
                        currency,
                        searchParamsRequest.getStartTripDate(),
                        searchParamsRequest.getEndTripDate());
            } else {
                outputFlightsList = getFirstStepFlightsByStartCityAndNextCountry(startLocation1,
                        endLocation1,
                        currency,
                        searchParamsRequest.getStartTripDate(),
                        searchParamsRequest.getEndTripDate());
            }
        } else {
            if (endLocation1.getLocationType().getName().equals(TYPE_CITY)) {
                outputFlightsList = getFirstStepFlightsByStartCountryAndNextCity(startLocation1,
                        endLocation1,
                        currency,
                        searchParamsRequest.getStartTripDate(),
                        searchParamsRequest.getEndTripDate());
            } else {
                outputFlightsList = getFirstStepFlightsByStartCountryAndNextCountry(startLocation1,
                        endLocation1,
                        currency,
                        searchParamsRequest.getStartTripDate(),
                        searchParamsRequest.getEndTripDate());
            }
        }

        int flightNumber = searchParamsRequest.getFlightNumber();

        for (int i = 1; i < flightNumber; i++) {
            Location startLocation =
                    locationRepository.findLocationByEntityId(searchParamsRequest.getStartPointsIds().get(i));
            Location endLocation =
                    locationRepository.findLocationByEntityId(searchParamsRequest.getEndPointsIds().get(i));
            if (startLocation.getLocationType().getName().equals(TYPE_CITY)) {
                if (endLocation.getLocationType().getName().equals(TYPE_CITY)) {
                    outputFlightsList = getNextStepFlightsByStartCityAndNextCity(outputFlightsList,
                            startLocation,
                            endLocation,
                            currency,
                            searchParamsRequest.getDaysInPointMin(),
                            searchParamsRequest.getDaysInPointMax(),
                            searchParamsRequest.getEndTripDate());
                } else {
                    outputFlightsList = getNextStepFlightsByStartCityAndNextCountry(outputFlightsList,
                            startLocation,
                            endLocation,
                            currency,
                            searchParamsRequest.getDaysInPointMin(),
                            searchParamsRequest.getDaysInPointMax(),
                            searchParamsRequest.getEndTripDate());
                }
            } else {
                if (endLocation.getLocationType().getName().equals(TYPE_CITY)) {
                    outputFlightsList = getNextStepFlightsByStartCountryAndNextCity(outputFlightsList,
                            startLocation,
                            endLocation,
                            currency,
                            searchParamsRequest.getDaysInPointMin(),
                            searchParamsRequest.getDaysInPointMax(),
                            searchParamsRequest.getEndTripDate());
                } else {
                    outputFlightsList = getNextStepFlightsByStartCountryAndNextCountry(outputFlightsList,
                            startLocation,
                            endLocation,
                            currency,
                            searchParamsRequest.getDaysInPointMin(),
                            searchParamsRequest.getDaysInPointMax(),
                            searchParamsRequest.getEndTripDate());
                }
            }
        }

        outputFlightsList = limitManyFlightsByDurability(outputFlightsList,
                searchParamsRequest.getTotalDaysMin(),
                searchParamsRequest.getTotalDaysMax());

        outputFlightsList = limitManyFlightsByPrice(outputFlightsList,
                searchParamsRequest.getTotalCostMin(),
                searchParamsRequest.getTotalCostMax());

        if (searchParamsRequest.isLowPriceSwitcher()) {
            return sortManyFlightsByPrice(outputFlightsList);
        } else {
            return outputFlightsList;
        }
    }
//    public List<QueryReturnFlightUnit> getReturnFlightsByRoutesAndCurrency(
//            Route departRoute, Route returnRoute, Currency currency, int minDuration, int maxDuration) {
//        LocalDate startDepartDate = LocalDate.now();
//        LocalDate endDepartDate = startDepartDate
//                .plusMonths(properties.getMaxMonths() + 1)
//                .minusDays(minDuration + 1);
//        List<Flight> departFlights = flightRepository.findFlightsByRouteAndCurrencyAndFlightDateBetweenAndActivityTrue(
//                departRoute, currency, startDepartDate, endDepartDate);
//
//        LocalDate startReturnDate = LocalDate.now().plusDays(minDuration);
//        LocalDate endReturnDate = startReturnDate
//                .plusMonths(properties.getMaxMonths() + 1)
//                .minusDays(1);
//        List<Flight> returnFlights = flightRepository.findFlightsByRouteAndCurrencyAndFlightDateBetweenAndActivityTrue(
//                returnRoute, currency, startReturnDate, endReturnDate);
//
//        List<QueryReturnFlightUnit> returnFlightUnits = new ArrayList<>();
//
//        for (Flight departFlight : departFlights) {
//            LocalDate departFlightDate = departFlight.getFlightDate();
//            for (int i = minDuration; i <= maxDuration; i++) {
//                LocalDate returnFlightDate = departFlightDate.plusDays(i);
//
//                Optional<Flight> returnFlightOptional = returnFlights.stream()
//                        .filter(flight -> flight.getFlightDate().equals(returnFlightDate))
//                        .findFirst();
//
//                returnFlightOptional.ifPresent(
//                        flight -> returnFlightUnits.add(new QueryReturnFlightUnit(departFlight, flight)));
//            }
//        }
//
//        return returnFlightUnits.stream()
//                .sorted(Comparator.comparingDouble(QueryReturnFlightUnit::getTotalCost))
//                .limit(properties.getShowFlightsNumber())
//                .collect(Collectors.toList());

//    }

//    public List<ManyFlightsUnit> getManyFlightsByRoutesListAndCurrency(
//            List<Route> routes, Currency currency, int minDuration, int maxDuration) {
//        List<ManyFlightsUnit> manyFlights = new ArrayList<>();
//
//        for (int i = 0; i < routes.size(); i++) {
//            if (i == 0) {
//                manyFlights = getFirstStepFlightsByRoute(routes.get(i), currency, minDuration);
//            } else {
//                manyFlights = getNextStepFlightsByRoute(manyFlights, routes.get(i), currency, minDuration, maxDuration);
//            }
//        }
//
//        return manyFlights;

//    }
//    public List<ManyFlightsUnit> getFirstStepFlightsByRoute(Route route, Currency currency, int minDuration) {
//        List<ManyFlightsUnit> outputFlightsList = new ArrayList<>();
//
//        LocalDate startDate = LocalDate.now();
//        LocalDate endDate = startDate
//                .plusMonths(properties.getMaxMonths() + 1)
//                .minusDays(minDuration + 1);
//
//        List<Flight> flightsList = flightRepository.findFlightsByRouteAndCurrencyAndFlightDateBetweenAndActivityTrue(
//                route, currency, startDate, endDate);
//
//        flightsList.forEach(flight -> outputFlightsList.add(new ManyFlightsUnit(flight)));
//
//        return outputFlightsList;

//    }
    private List<ManyFlightsUnit> getFirstStepFlightsByStartCityAndNextCity(
            Location startCity, Location nextCity, Currency currency, LocalDate startDate, LocalDate endDate) {

        List<ManyFlightsUnit> outputFlightsList = new ArrayList<>();

        Route route = routeRepository.findRouteByFromCityAndToCity(startCity, nextCity);
        List<Flight> flightsList =
                flightRepository.findFlightsByRouteAndCurrencyAndFlightDateBetweenAndActivityTrue(
                        route, currency, startDate, endDate);
        flightsList.forEach(flight -> outputFlightsList.add(new ManyFlightsUnit(flight)));

        return outputFlightsList;
    }

    private List<ManyFlightsUnit> getFirstStepFlightsByStartCityAndNextCountry(
            Location startCity, Location nextCountry, Currency currency, LocalDate startDate, LocalDate endDate) {

        List<ManyFlightsUnit> outputFlightsList = new ArrayList<>();

        long countryEntityId = nextCountry.getEntityId();
        List<Location> countryCitiesInOperation = cityRepository.findAll().stream()
                .map(CityInOperation::getLocation)
                .filter(location -> location.getParentId() == countryEntityId)
                .collect(Collectors.toList());

        for (Location endLocation : countryCitiesInOperation) {
            Route route = routeRepository.findRouteByFromCityAndToCity(startCity, endLocation);
            List<Flight> flightsList =
                    flightRepository.findFlightsByRouteAndCurrencyAndFlightDateBetweenAndActivityTrue(
                            route, currency, startDate, endDate);
            flightsList.forEach(flight -> outputFlightsList.add(new ManyFlightsUnit(flight)));
        }

        return outputFlightsList;
    }

    private List<ManyFlightsUnit> getFirstStepFlightsByStartCountryAndNextCity(
            Location startCountry, Location nextCity, Currency currency, LocalDate startDate, LocalDate endDate) {

        List<ManyFlightsUnit> outputFlightsList = new ArrayList<>();

        long countryEntityId = startCountry.getEntityId();
        List<Location> countryCitiesInOperation = cityRepository.findAll().stream()
                .map(CityInOperation::getLocation)
                .filter(location -> location.getParentId() == countryEntityId)
                .collect(Collectors.toList());

        for (Location startCity : countryCitiesInOperation) {
            Route route = routeRepository.findRouteByFromCityAndToCity(startCity, nextCity);
            List<Flight> flightsList =
                    flightRepository.findFlightsByRouteAndCurrencyAndFlightDateBetweenAndActivityTrue(
                            route, currency, startDate, endDate);
            flightsList.forEach(flight -> outputFlightsList.add(new ManyFlightsUnit(flight)));
        }

        return outputFlightsList;
    }

    private List<ManyFlightsUnit> getFirstStepFlightsByStartCountryAndNextCountry(
            Location startCountry, Location nextCountry, Currency currency, LocalDate startDate, LocalDate endDate) {

        List<ManyFlightsUnit> outputFlightsList = new ArrayList<>();

        long startCountryEntityId = startCountry.getEntityId();
        List<Location> startCountryCitiesInOperation = cityRepository.findAll().stream()
                .map(CityInOperation::getLocation)
                .filter(location -> location.getParentId() == startCountryEntityId)
                .collect(Collectors.toList());

        long nextCountryEntityId = nextCountry.getEntityId();
        List<Location> nextCountryCitiesInOperation = cityRepository.findAll().stream()
                .map(CityInOperation::getLocation)
                .filter(location -> location.getParentId() == nextCountryEntityId)
                .collect(Collectors.toList());

        for (Location startCity : startCountryCitiesInOperation) {
            for (Location nextCity : nextCountryCitiesInOperation) {
                Route route = routeRepository.findRouteByFromCityAndToCity(startCity, nextCity);
                List<Flight> flightsList =
                        flightRepository.findFlightsByRouteAndCurrencyAndFlightDateBetweenAndActivityTrue(
                                route, currency, startDate, endDate);
                flightsList.forEach(flight -> outputFlightsList.add(new ManyFlightsUnit(flight)));
            }
        }

        return outputFlightsList;
    }

//    private List<ManyFlightsUnit> getNextStepFlightsByRoute(
//            List<ManyFlightsUnit> inputFlightsList, Route route, Currency currency, int minDuration, int maxDuration) {
//
//        List<ManyFlightsUnit> outputFlightsList = new ArrayList<>();
//
//        LocalDate startReturnDate = LocalDate.now().plusDays(minDuration);
//        LocalDate endReturnDate = startReturnDate
//                .plusMonths(properties.getMaxMonths() + 1)
//                .minusDays(1);
//        List<Flight> nextFlights = flightRepository.findFlightsByRouteAndCurrencyAndFlightDateBetweenAndActivityTrue(
//                route, currency, startReturnDate, endReturnDate);
//
//        for (ManyFlightsUnit inputFlightsUnit : inputFlightsList) {
//            Flight lastFlight = inputFlightsUnit.getLastFlight();
//
//            for (int i = minDuration; i <= maxDuration; i++) {
//                LocalDate nextFlightDate = lastFlight.getFlightDate().plusDays(i);
//
//                Optional<Flight> nextFlightOptional = nextFlights.stream()
//                        .filter(flight -> flight.getFlightDate().equals(nextFlightDate))
//                        .findFirst();
//
//                if (nextFlightOptional.isPresent()) {
//                    ManyFlightsUnit newManyFlightsUnit =
//                            inputFlightsUnit.getNewCopyAndAddFlight(nextFlightOptional.get());
//                    outputFlightsList.add(newManyFlightsUnit);
//                }
//            }
//        }
//
//        return outputFlightsList;

//    }
    private List<ManyFlightsUnit> getNextStepFlightsByStartCityAndNextCity(
            List<ManyFlightsUnit> inputFlightsList, Location startCity, Location nextCity, Currency currency,
            int minDuration, int maxDuration, LocalDate endDate) {

        List<ManyFlightsUnit> outputFlightsList = new ArrayList<>();

        for (ManyFlightsUnit inputFlightsUnit : inputFlightsList) {
            Flight lastFlight = inputFlightsUnit.getLastFlight();

            LocalDate startDate = lastFlight.getFlightDate().plusDays(minDuration);

            Route route = routeRepository.findRouteByFromCityAndToCity(startCity, nextCity);

            List<Flight> nextFlights =
                    flightRepository.findFlightsByRouteAndCurrencyAndFlightDateBetweenAndActivityTrue(
                            route, currency, startDate, endDate);

            for (int i = minDuration; i <= maxDuration; i++) {
                LocalDate nextFlightDate = lastFlight.getFlightDate().plusDays(i);

                Optional<Flight> nextFlightOptional = nextFlights.stream()
                        .filter(flight -> flight.getFlightDate().equals(nextFlightDate))
                        .findFirst();

                if (nextFlightOptional.isPresent()) {
                    ManyFlightsUnit newManyFlightsUnit =
                            inputFlightsUnit.getNewCopyAndAddFlight(nextFlightOptional.get());
                    outputFlightsList.add(newManyFlightsUnit);
                }
            }
        }

        return outputFlightsList;
    }

    private List<ManyFlightsUnit> getNextStepFlightsByStartCityAndNextCountry(
            List<ManyFlightsUnit> inputFlightsList, Location startCity, Location nextCountry, Currency currency,
            int minDuration, int maxDuration, LocalDate endDate) {

        List<ManyFlightsUnit> outputFlightsList = new ArrayList<>();

        long countryEntityId = nextCountry.getEntityId();
        List<Location> countryCitiesInOperation = cityRepository.findAll().stream()
                .map(CityInOperation::getLocation)
                .filter(location -> location.getParentId() == countryEntityId)
                .collect(Collectors.toList());

        for (ManyFlightsUnit inputFlightsUnit : inputFlightsList) {
            Flight lastFlight = inputFlightsUnit.getLastFlight();

            LocalDate startDate = lastFlight.getFlightDate().plusDays(minDuration);

            for (Location endLocation : countryCitiesInOperation) {
                Route route = routeRepository.findRouteByFromCityAndToCity(startCity, endLocation);

                List<Flight> nextFlights =
                        flightRepository.findFlightsByRouteAndCurrencyAndFlightDateBetweenAndActivityTrue(
                                route, currency, startDate, endDate);

                for (int i = minDuration; i <= maxDuration; i++) {
                    LocalDate nextFlightDate = lastFlight.getFlightDate().plusDays(i);

                    Optional<Flight> nextFlightOptional = nextFlights.stream()
                            .filter(flight -> flight.getFlightDate().equals(nextFlightDate))
                            .findFirst();

                    if (nextFlightOptional.isPresent()) {
                        ManyFlightsUnit newManyFlightsUnit =
                                inputFlightsUnit.getNewCopyAndAddFlight(nextFlightOptional.get());
                        outputFlightsList.add(newManyFlightsUnit);
                    }
                }
            }
        }
        return outputFlightsList;
    }

    private List<ManyFlightsUnit> getNextStepFlightsByStartCountryAndNextCity(
            List<ManyFlightsUnit> inputFlightsList, Location startCountry, Location nextCity, Currency currency,
            int minDuration, int maxDuration, LocalDate endDate) {

        List<ManyFlightsUnit> outputFlightsList = new ArrayList<>();

        long startCountryEntityId = startCountry.getEntityId();
        List<Location> startCountryCitiesInOperation = cityRepository.findAll().stream()
                .map(CityInOperation::getLocation)
                .filter(location -> location.getParentId() == startCountryEntityId)
                .collect(Collectors.toList());

        for (ManyFlightsUnit inputFlightsUnit : inputFlightsList) {
            Flight lastFlight = inputFlightsUnit.getLastFlight();
            for (Location startLocation : startCountryCitiesInOperation) {

                LocalDate startDate = lastFlight.getFlightDate().plusDays(minDuration);

                Route route = routeRepository.findRouteByFromCityAndToCity(startLocation, nextCity);

                List<Flight> nextFlights =
                        flightRepository.findFlightsByRouteAndCurrencyAndFlightDateBetweenAndActivityTrue(
                                route, currency, startDate, endDate);

                for (int i = minDuration; i <= maxDuration; i++) {
                    LocalDate nextFlightDate = lastFlight.getFlightDate().plusDays(i);

                    Optional<Flight> nextFlightOptional = nextFlights.stream()
                            .filter(flight -> flight.getFlightDate().equals(nextFlightDate))
                            .findFirst();

                    if (nextFlightOptional.isPresent()) {
                        ManyFlightsUnit newManyFlightsUnit =
                                inputFlightsUnit.getNewCopyAndAddFlight(nextFlightOptional.get());
                        outputFlightsList.add(newManyFlightsUnit);
                    }
                }
            }
        }

        return outputFlightsList;
    }

    private List<ManyFlightsUnit> getNextStepFlightsByStartCountryAndNextCountry(
            List<ManyFlightsUnit> inputFlightsList, Location startCountry, Location nextCountry, Currency currency,
            int minDuration, int maxDuration, LocalDate endDate) {

        List<ManyFlightsUnit> outputFlightsList = new ArrayList<>();

        long startCountryEntityId = startCountry.getEntityId();
        List<Location> startCountryCitiesInOperation = cityRepository.findAll().stream()
                .map(CityInOperation::getLocation)
                .filter(location -> location.getParentId() == startCountryEntityId)
                .collect(Collectors.toList());

        long nextCountryEntityId = nextCountry.getEntityId();
        List<Location> nextCountryCitiesInOperation = cityRepository.findAll().stream()
                .map(CityInOperation::getLocation)
                .filter(location -> location.getParentId() == nextCountryEntityId)
                .collect(Collectors.toList());

        for (ManyFlightsUnit inputFlightsUnit : inputFlightsList) {
            Flight lastFlight = inputFlightsUnit.getLastFlight();
            for (Location startLocation : startCountryCitiesInOperation) {

                LocalDate startDate = lastFlight.getFlightDate().plusDays(minDuration);

                for (Location endLocation : nextCountryCitiesInOperation) {
                    Route route = routeRepository.findRouteByFromCityAndToCity(startLocation, endLocation);

                    List<Flight> nextFlights =
                            flightRepository.findFlightsByRouteAndCurrencyAndFlightDateBetweenAndActivityTrue(
                                    route, currency, startDate, endDate);

                    for (int i = minDuration; i <= maxDuration; i++) {
                        LocalDate nextFlightDate = lastFlight.getFlightDate().plusDays(i);

                        Optional<Flight> nextFlightOptional = nextFlights.stream()
                                .filter(flight -> flight.getFlightDate().equals(nextFlightDate))
                                .findFirst();

                        if (nextFlightOptional.isPresent()) {
                            ManyFlightsUnit newManyFlightsUnit =
                                    inputFlightsUnit.getNewCopyAndAddFlight(nextFlightOptional.get());
                            outputFlightsList.add(newManyFlightsUnit);
                        }
                    }
                }
            }
        }
        return outputFlightsList;
    }

    private List<ManyFlightsUnit> limitManyFlightsByPrice(List<ManyFlightsUnit> inputFlightsList,
                                                          int minPrice, int maxPrice) {
        return inputFlightsList.stream()
                .filter(manyFlightsUnit -> manyFlightsUnit.getTotalCost() >= minPrice &&
                        manyFlightsUnit.getTotalCost() <= maxPrice)
                .collect(Collectors.toList());
    }

    private List<ManyFlightsUnit> limitManyFlightsByDurability(List<ManyFlightsUnit> inputFlightsList,
                                                               int minDays, int maxDays) {
        return inputFlightsList.stream()
                .filter(manyFlightsUnit -> {
                    long daysBetween = ChronoUnit.DAYS.between(
                            manyFlightsUnit.getFlights().get(0).getFlightDate(),
                            manyFlightsUnit.getLastFlight().getFlightDate());
                    return daysBetween >= minDays && daysBetween <= maxDays;
                })
                .collect(Collectors.toList());
    }

    private List<ManyFlightsUnit> sortManyFlightsByPrice(List<ManyFlightsUnit> inputFlightsList) {
        return inputFlightsList.stream()
                .sorted(Comparator.comparingDouble(ManyFlightsUnit::getTotalCost))
                .collect(Collectors.toList());
    }
}