package org.geekhub.kovalchuk.repository.jpa;

import org.geekhub.kovalchuk.model.entity.Location;
import org.geekhub.kovalchuk.model.entity.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findLocationByEntityId(long entityId);
    Location findLocationByNameAndLocationTypeAndActivityTrue(String name, LocationType locationType);
    List<Location> findLocationsByLocationTypeAndActivityTrue(LocationType locationType);
    List<Location> findLocationsByParentId(long parentId);
    List<Location> findLocationsByParentIdIn(List<Long> parentIds);
    Location findLocationByIataAndLocationType(String iata, LocationType locationType);
}