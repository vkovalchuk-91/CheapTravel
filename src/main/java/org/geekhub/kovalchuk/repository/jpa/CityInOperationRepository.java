package org.geekhub.kovalchuk.repository.jpa;

import org.geekhub.kovalchuk.model.entity.CityInOperation;
import org.geekhub.kovalchuk.model.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityInOperationRepository extends JpaRepository<CityInOperation, Long> {
    CityInOperation findCityInOperationByLocation(Location location);
    List<CityInOperation> findCityInOperationByActivityTrue();
}