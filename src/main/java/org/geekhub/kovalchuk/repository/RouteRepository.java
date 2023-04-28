package org.geekhub.kovalchuk.repository;

import org.geekhub.kovalchuk.model.entity.Location;
import org.geekhub.kovalchuk.model.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    Route findRouteByFromCityAndToCity(Location fromCity, Location toCity);
}