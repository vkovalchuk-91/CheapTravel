package org.geekhub.kovalchuk.repository;

import org.geekhub.kovalchuk.model.entity.CityInOperation;
import org.geekhub.kovalchuk.model.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityInOperationRepository extends JpaRepository<CityInOperation, Long> {
    CityInOperation findCityInOperationByLocation(Location location);
}