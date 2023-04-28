package org.geekhub.kovalchuk.repository;

import org.geekhub.kovalchuk.model.entity.Currency;
import org.geekhub.kovalchuk.model.entity.Flight;
import org.geekhub.kovalchuk.model.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findFlightsByRouteAndCurrencyAndFlightDateBetween(
            Route route, Currency currency, LocalDate start, LocalDate end);
    List<Flight> findFlightsByRouteAndCurrencyAndFlightDateBetweenAndActivityTrue(
            Route route, Currency currency, LocalDate start, LocalDate end);

    Flight findFlightByRouteAndCurrencyAndFlightDate(
            Route route, Currency currency, LocalDate flightDate);
}