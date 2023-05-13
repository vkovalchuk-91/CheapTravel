package org.geekhub.kovalchuk.service;

import org.geekhub.kovalchuk.config.ApplicationPropertiesConfig;
import org.geekhub.kovalchuk.json.JsonParser;
import org.geekhub.kovalchuk.json.JsonRequestMaker;
import org.geekhub.kovalchuk.json.entity.LocationJsonResponse.Place;
import org.geekhub.kovalchuk.model.dto.AbbreviationsSelectorDto;
import org.geekhub.kovalchuk.model.entity.Location;
import org.geekhub.kovalchuk.model.entity.LocationType;
import org.geekhub.kovalchuk.repository.jpa.LocationRepository;
import org.geekhub.kovalchuk.repository.jpa.LocationTypeRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LocationsService {
    private final LocationRepository locationRepository;
    private final LocationTypeRepository locationTypeRepository;
    private final ApplicationPropertiesConfig properties;
    private static final String TYPE_CONTINENT = "PLACE_TYPE_CONTINENT";
    private static final String TYPE_CITY = "PLACE_TYPE_CITY";
    private static final String TYPE_AIRPORT = "PLACE_TYPE_AIRPORT";

    public LocationsService(LocationRepository locationRepository,
                            LocationTypeRepository locationTypeRepository,
                            ApplicationPropertiesConfig properties) {
        this.locationRepository = locationRepository;
        this.locationTypeRepository = locationTypeRepository;
        this.properties = properties;
    }

    public boolean isLocationTableEmpty() {
        return locationRepository.count() == 0;
    }

    public void updateAbbreviations() {
        String[] iataAndAbbreviationPair = properties.getCitiesAbbreviations().split(", ");
        LocationType locationType = locationTypeRepository.findLocationTypeEntityByName(TYPE_CITY);
        for (String city : iataAndAbbreviationPair) {
            String[] cityAbbr = city.split(" ");
            Location location = locationRepository.findLocationByIataAndLocationType(
                    cityAbbr[0], locationType);
            location.setSkyScannerAbbreviation(cityAbbr[1]);
            locationRepository.save(location);
        }
    }

    public void saveOrUpdateLocationsToDb() {
        String locationsJson;
        try {
            locationsJson = JsonRequestMaker.getLocationsJson();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Collection<Place> places = JsonParser.getLocations(locationsJson).getPlaces().values();

        List<Long> oldLocationEntityIds = locationRepository.findAll().stream()
                .map(Location::getEntityId)
                .collect(Collectors.toList());
        List<Long> newLocationEntityIds = places.stream()
                .map(Place::getEntityId)
                .collect(Collectors.toList());

        for (Long prevId : oldLocationEntityIds) {
            if (!newLocationEntityIds.contains(prevId)) {
                Location location = locationRepository.findLocationByEntityId(prevId);
                location.setActivity(false);
                locationRepository.save(location);
            }
        }

        int addedLocationsCounter = 0;
        for (Long id : newLocationEntityIds) {
            if (!oldLocationEntityIds.contains(id)) {
                Place place = places.stream()
                        .filter(p -> id.equals(p.getEntityId()))
                        .collect(Collectors.toList()).get(0);

                String locationTypeName = place.getType().name();
                LocationType locationType = locationTypeRepository.findLocationTypeEntityByName(locationTypeName);
                if (locationType == null) {
                    locationType = new LocationType();
                    locationType.setName(locationTypeName);
                    locationTypeRepository.save(locationType);
                }

                Location currentLocation = new Location();
                currentLocation.setEntityId(place.getEntityId());
                currentLocation.setParentId(place.getParentId());
                currentLocation.setName(place.getName());
                currentLocation.setLocationType(locationType);
                currentLocation.setIata(place.getIata());
                currentLocation.setLatitude(place.getCoordinates().getLatitude());
                currentLocation.setLongitude(place.getCoordinates().getLongitude());
                currentLocation.setAddDate(LocalDateTime.now());
                currentLocation.setSkyScannerAbbreviation(place.getIata());
                currentLocation.setActivity(true);

                locationRepository.save(currentLocation);
                addedLocationsCounter++;
            }
        }

        System.out.println("Locations updater is running, added " + addedLocationsCounter + " new location(s)!");
    }

    public AbbreviationsSelectorDto getEuropeanCitiesAbbreviationsForView() {
        LocationType continentType = locationTypeRepository.findLocationTypeEntityByName(TYPE_CONTINENT);
        Location europe = locationRepository.findLocationByNameAndLocationTypeAndActivityTrue("Європа", continentType);
        List<Location> europeCountries = locationRepository.findLocationsByParentId(europe.getEntityId());
        List<Long> europeCountriesIds = europeCountries.stream()
                .map(Location::getEntityId)
                .collect(Collectors.toList());

        LocationType airportType = locationTypeRepository.findLocationTypeEntityByName(TYPE_AIRPORT);
        List<Long> airportsIds = locationRepository.findLocationsByLocationTypeAndActivityTrue(airportType).stream()
                .map(Location::getParentId)
                .collect(Collectors.toList());
        List<Location> europeCities = locationRepository.findLocationsByParentIdIn(europeCountriesIds).stream()
                .filter(location -> airportsIds.contains(location.getEntityId()))
                .collect(Collectors.toList());

        Map<String, List<AbbreviationsSelectorDto.City>> europeCitiesWithCountry = new HashMap<>();
        for (Location country : europeCountries) {
            long countryEntityId = country.getEntityId();
            List<Location> countryCities = europeCities.stream()
                    .filter(location -> location.getParentId() == countryEntityId)
                    .collect(Collectors.toList());

            List<AbbreviationsSelectorDto.City> cities = countryCities.stream()
                    .map(cityLocation -> new AbbreviationsSelectorDto.City(cityLocation.getEntityId(),
                            cityLocation.getName(), cityLocation.getSkyScannerAbbreviation()))
                    .collect(Collectors.toList());

            europeCitiesWithCountry.put(country.getName(), cities);
        }

        return new AbbreviationsSelectorDto(europeCitiesWithCountry);
    }

    public void updateEuropeanCitiesAbbreviations(Map<Long, String> updatedCities) {
        for (Map.Entry<Long, String> city : updatedCities.entrySet()) {
            long cityId = city.getKey();
            String skyScannerAbbreviation = city.getValue();

            Location location = locationRepository.findLocationByEntityId(cityId);
            if (!location.getSkyScannerAbbreviation().equals(skyScannerAbbreviation)) {
                location.setSkyScannerAbbreviation(skyScannerAbbreviation);
                locationRepository.save(location);
            }
        }
    }
}