package org.geekhub.kovalchuk.repository.jpa;

import org.geekhub.kovalchuk.model.entity.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationTypeRepository extends JpaRepository<LocationType, Long> {
    LocationType findLocationTypeEntityByName(String name);
}