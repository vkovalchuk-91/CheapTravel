package org.geekhub.kovalchuk.repository.jpa;

import org.geekhub.kovalchuk.model.entity.Location;
import org.geekhub.kovalchuk.model.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    Route findRouteByFromCityAndToCity(Location fromCity, Location toCity);
    List<Route> findRoutesByFromCityAndHaveFlightsTrueAndActivityTrue(Location fromCity);
    List<Route> findRoutesByToCityAndHaveFlightsTrueAndActivityTrue(Location toCity);
    List<Route> findRoutesByHaveFlightsTrue();
}