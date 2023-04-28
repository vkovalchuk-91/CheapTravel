package org.geekhub.kovalchuk.service;

import org.geekhub.kovalchuk.config.PropertiesConfig;
import org.geekhub.kovalchuk.json.JsonParser;
import org.geekhub.kovalchuk.json.JsonRequestMaker;
import org.geekhub.kovalchuk.json.entity.MonthPriceJsonEntity.Content.Results.Quote;
import org.geekhub.kovalchuk.model.entity.Currency;
import org.geekhub.kovalchuk.model.entity.Flight;
import org.geekhub.kovalchuk.model.entity.Route;
import org.geekhub.kovalchuk.repository.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
public class MonthPricesService {
    FlightRepository flightRepository;
    RouteRepository routeRepository;
    TaskQueueRepository taskQueueRepository;
    PropertiesConfig properties;

    public MonthPricesService(FlightRepository flightRepository,
                              RouteRepository routeRepository,
                              TaskQueueRepository taskQueueRepository,
                              PropertiesConfig properties) {
        this.flightRepository = flightRepository;
        this.routeRepository = routeRepository;
        this.taskQueueRepository = taskQueueRepository;
        this.properties = properties;
    }

    public void runFlightPricesUpdater(Currency currency) {
        LocalDate currentDate = LocalDate.now();

        List<Route> allRoutes = routeRepository.findAll();
        for (Route route : allRoutes) {
            if (route.isHaveFlights()) {
                for (int i = 0; i < properties.getMaxMonths(); i++) {
                    LocalDate date = currentDate.plusMonths(i);
                    int year = date.getYear();
                    int month = date.getMonthValue();

                    try {
                        addRequestTaskToQueue(currency, route, year, month);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private void addRequestTaskToQueue(Currency currency, Route route, int year, int month) throws IOException {
        MonthPricesSaveToDbTask task = new MonthPricesSaveToDbTask(currency, route, year, month);
        taskQueueRepository.getTaskQueue().add(task);
    }

    private void saveOrUpdateFlightsToDb(Currency currency, Route route, int year, int month) throws IOException {
        String monthPricesJson = JsonRequestMaker.getMonthPricesJson(
                currency.getCode(), route.getFromCity().getEntityId(), route.getToCity().getEntityId(), year, month);
        Collection<Quote> monthPricesRawData =
                JsonParser.getMonthPrices(monthPricesJson).getContent().getResults().getQuotes().values();

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        List<Flight> currentFlights =
                flightRepository.findFlightsByRouteAndCurrencyAndFlightDateBetween(route, currency, startDate, endDate);
        currentFlights.forEach(flight -> flight.setActivity(false));

        for (Quote flightRawData : monthPricesRawData) {
            if (flightRawData.getIsDirect()) {
                int day = flightRawData.getOutboundLeg().getDepartureDateTime().getDay();
                LocalDate currentDate = LocalDate.of(year, month, day);
                double minPrice = flightRawData.getMinPrice().getAmount();
                Flight flight = flightRepository.findFlightByRouteAndCurrencyAndFlightDate(
                        route, currency, currentDate);
                if (flight == null) {
                    flight = new Flight();
                    flight.setRoute(route);
                    flight.setFlightDate(currentDate);
                    flight.setCurrency(currency);
                }

                flight.setPrice(minPrice);
                flight.setAddDate(LocalDateTime.now());
                flight.setActivity(true);

                flightRepository.save(flight);
            }
        }
    }

    private class MonthPricesSaveToDbTask implements Runnable {
        Currency currency;
        Route route;
        int year;
        int month;

        public MonthPricesSaveToDbTask(Currency currency, Route route, int year, int month) {
            this.currency = currency;
            this.route = route;
            this.year = year;
            this.month = month;
        }

        @Override
        public void run() {
            try {
                saveOrUpdateFlightsToDb(currency, route, year, month);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
