package org.geekhub.kovalchuk.service;

import org.geekhub.kovalchuk.json.entity.LocationJsonEntity.Place;
import org.geekhub.kovalchuk.model.entity.Location;
import org.geekhub.kovalchuk.model.entity.LocationType;
import org.geekhub.kovalchuk.json.JsonParser;
import org.geekhub.kovalchuk.json.JsonRequestMaker;
import org.geekhub.kovalchuk.repository.LocationRepository;
import org.geekhub.kovalchuk.repository.LocationTypeRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class LocationsService {
    public static final String TYPE_CITY = "PLACE_TYPE_CITY";
    public static final String TYPE_COUNTRY = "PLACE_TYPE_COUNTRY";
    LocationRepository locationRepository;
    LocationTypeRepository locationTypeRepository;

    public LocationsService(LocationRepository locationRepository, LocationTypeRepository locationTypeRepository) {
        this.locationRepository = locationRepository;
        this.locationTypeRepository = locationTypeRepository;
    }

    public boolean isLocationTableEmpty() {
        return locationRepository.count() == 0;
    }

    public void saveOrUpdateLocationsToDb() throws IOException {
        String locationsJson = JsonRequestMaker.getLocationsJson();
        Collection<Place> places = JsonParser.getLocations(locationsJson).getPlaces().values();

        for (Place place: places) {

            String locationTypeName = place.getType().name();
            LocationType locationType = locationTypeRepository.findLocationTypeEntityByName(locationTypeName);
            if (locationType == null) {
                locationType = new LocationType();
                locationType.setName(locationTypeName);
                locationTypeRepository.save(locationType);
            }

            Location location = new Location();
            location.setEntityId(place.getEntityId());
            location.setParentId(place.getParentId());
            location.setName(place.getName());
            location.setLocationType(locationType);
            location.setIata(place.getIata());
            location.setLatitude(place.getCoordinates().getLatitude());
            location.setLongitude(place.getCoordinates().getLongitude());
            location.setAddDate(LocalDateTime.now());

            locationRepository.save(location);
        }
    }

    public Location getLocationByCountryName(String countryName) {
        LocationType locationType = locationTypeRepository.findLocationTypeEntityByName(TYPE_COUNTRY);
        return locationRepository.findLocationByNameAndLocationType(countryName, locationType);
    }

    public Location getLocationByCityName(String cityName) {
        LocationType locationType = locationTypeRepository.findLocationTypeEntityByName(TYPE_CITY);
        return locationRepository.findLocationByNameAndLocationType(cityName, locationType);
    }
}
