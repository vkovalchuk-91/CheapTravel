package org.geekhub.kovalchuk.repository;

import org.geekhub.kovalchuk.model.entity.Location;
import org.geekhub.kovalchuk.model.entity.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findLocationByEntityId(long entityId);
    Location findLocationByNameAndLocationType(String name, LocationType locationType);
}