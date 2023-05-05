package org.geekhub.kovalchuk.service;

import org.geekhub.kovalchuk.config.ApplicationPropertiesConfig;
import org.geekhub.kovalchuk.model.dto.CitiesSelectorDto;
import org.geekhub.kovalchuk.model.dto.CitiesSelectorDto.City;
import org.geekhub.kovalchuk.model.dto.LocationInOperationDto;
import org.geekhub.kovalchuk.model.entity.CityInOperation;
import org.geekhub.kovalchuk.model.entity.Location;
import org.geekhub.kovalchuk.model.entity.LocationType;
import org.geekhub.kovalchuk.repository.TaskQueueRepository;
import org.geekhub.kovalchuk.repository.jpa.CityInOperationRepository;
import org.geekhub.kovalchuk.repository.jpa.LocationRepository;
import org.geekhub.kovalchuk.repository.jpa.LocationTypeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CityInOperationService {
    public static final String TYPE_CONTINENT = "PLACE_TYPE_CONTINENT";
    public static final String TYPE_AIRPORT = "PLACE_TYPE_AIRPORT";
    CityInOperationRepository cityInOperationRepository;
    LocationRepository locationRepository;
    LocationTypeRepository locationTypeRepository;
    TaskQueueRepository taskQueueRepository;
    ApplicationPropertiesConfig properties;
    private List<LocationInOperationDto> cashedLocationsInOperation = new ArrayList<>();
    private List<LocationInOperationDto> cashedCitiesInOperation = new ArrayList<>();

    public CityInOperationService(CityInOperationRepository cityInOperationRepository,
                                  LocationRepository locationRepository,
                                  LocationTypeRepository locationTypeRepository,
                                  TaskQueueRepository taskQueueRepository,
                                  ApplicationPropertiesConfig properties) {
        this.cityInOperationRepository = cityInOperationRepository;
        this.locationRepository = locationRepository;
        this.locationTypeRepository = locationTypeRepository;
        this.taskQueueRepository = taskQueueRepository;
        this.properties = properties;
    }

    public boolean isCityInOperationTableEmpty() {
        return cityInOperationRepository.count() == 0;
    }

    public void addOrUpdateTop100AirportCitiesToDb() {
        List<Integer> topCitiesInOperationEntityId =
                Arrays.stream(properties.getTopCitiesInOperationEntityId().split(", "))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

        for (Integer cityEntityId : topCitiesInOperationEntityId) {
            Location cityLocation = locationRepository.findLocationByEntityId(cityEntityId);
            CityInOperation cityInOperation = cityInOperationRepository.findCityInOperationByLocation(cityLocation);
            if (cityInOperation == null) {
                cityInOperation = new CityInOperation();
                cityInOperation.setLocation(cityLocation);
            }
            cityInOperation.setActivity(true);
            cityInOperation.setAddDate(LocalDateTime.now());
            cityInOperation.setActivatingDate(LocalDateTime.now());

            cityInOperationRepository.save(cityInOperation);
        }
    }

    public void activateCity(long cityEntityId) {
        Location cityLocation = locationRepository.findLocationByEntityId(cityEntityId);
        CityInOperation cityInOperation = cityInOperationRepository.findCityInOperationByLocation(cityLocation);
        if (cityInOperation == null) {
            cityInOperation = new CityInOperation();
            cityInOperation.setLocation(cityLocation);
        }
        cityInOperation.setActivity(true);
        cityInOperation.setAddDate(LocalDateTime.now());
        cityInOperation.setActivatingDate(LocalDateTime.now());

        cityInOperationRepository.save(cityInOperation);
    }

    public void deactivateCity(long cityEntityId) {
        Location cityLocation = locationRepository.findLocationByEntityId(cityEntityId);
        CityInOperation cityInOperation = cityInOperationRepository.findCityInOperationByLocation(cityLocation);
        if (cityInOperation != null) {
            cityInOperation.setActivity(false);
            cityInOperation.setActivatingDate(null);
            cityInOperationRepository.save(cityInOperation);
        }
    }

    public void deleteCityFromDb(long cityEntityId) {
        Location cityLocation = locationRepository.findLocationByEntityId(cityEntityId);
        CityInOperation cityInOperation = cityInOperationRepository.findCityInOperationByLocation(cityLocation);
        if (cityInOperation != null) {
            cityInOperationRepository.delete(cityInOperation);
        }
    }

    public List<LocationInOperationDto> getLocationsForView() {
        if (cashedLocationsInOperation.isEmpty()) {
            updateCashedLocationsInOperation();
        }
        return cashedLocationsInOperation;
    }

    public void updateCashedLocationsInOperation() {
        List<Location> citiesInOperation = cityInOperationRepository.findCityInOperationByActivityTrue().stream()
                .map(CityInOperation::getLocation)
                .collect(Collectors.toList());

        List<Location> countriesInOperation = citiesInOperation.stream()
                .map(Location::getParentId)
                .map(id -> locationRepository.getById(id))
                .distinct()
                .collect(Collectors.toList());

        List<LocationInOperationDto> countryWithCitiesLocations = new ArrayList<>();
        countriesInOperation
                .forEach(country -> {
                    countryWithCitiesLocations.add(new LocationInOperationDto(
                            country.getEntityId(), false, country.getName()));
                    citiesInOperation.stream()
                            .filter(city -> city.getParentId() == country.getEntityId())
                            .forEach(city -> countryWithCitiesLocations.add(new LocationInOperationDto(
                                    city.getEntityId(), true, city.getName())));
                });


        cashedLocationsInOperation = countryWithCitiesLocations;
    }

    public List<LocationInOperationDto> getCitiesForView() {
        if (cashedCitiesInOperation.isEmpty()) {
            updateCashedCitiesInOperation();
        }
        return cashedCitiesInOperation;
    }

    public void updateCashedCitiesInOperation() {
        List<Location> citiesInOperation = cityInOperationRepository.findCityInOperationByActivityTrue().stream()
                .map(CityInOperation::getLocation)
                .collect(Collectors.toList());

        List<LocationInOperationDto> cities = new ArrayList<>();
        citiesInOperation.forEach(city -> cities.add(new LocationInOperationDto(
                city.getEntityId(), true, city.getName())));

        cashedCitiesInOperation = cities;
    }

    public CitiesSelectorDto getEuropeanCitiesForView() {
        LocationType continentType = locationTypeRepository.findLocationTypeEntityByName(TYPE_CONTINENT);
        Location europe = locationRepository.findLocationByNameAndLocationType("Європа", continentType);
        List<Location> europeCountries = locationRepository.findLocationsByParentId(europe.getEntityId());
        List<Long> europeCountriesIds = europeCountries.stream()
                .map(Location::getEntityId)
                .collect(Collectors.toList());

        LocationType airportType = locationTypeRepository.findLocationTypeEntityByName(TYPE_AIRPORT);
        List<Long> airportsIds = locationRepository.findLocationsByLocationType(airportType).stream()
                .map(Location::getParentId)
                .collect(Collectors.toList());
        List<Location> europeCities = locationRepository.findLocationsByParentIdIn(europeCountriesIds).stream()
                .filter(location -> airportsIds.contains(location.getEntityId()))
                .collect(Collectors.toList());

        List<Location> citiesInOperation = cityInOperationRepository.findCityInOperationByActivityTrue().stream()
                .map(CityInOperation::getLocation)
                .collect(Collectors.toList());

        Map<String, List<City>> europeCitiesWithCountry = new HashMap<>();
        for (Location country : europeCountries) {
            long countryEntityId = country.getEntityId();
            List<Location> countryCities = europeCities.stream()
                    .filter(location -> location.getParentId() == countryEntityId)
                    .collect(Collectors.toList());

            List<City> cities = countryCities.stream()
                    .map(cityLocation -> new City(cityLocation.getEntityId(),
                            cityLocation.getName(),
                            citiesInOperation.contains(cityLocation)))
                    .collect(Collectors.toList());

            europeCitiesWithCountry.put(country.getName(), cities);
        }

        return new CitiesSelectorDto(europeCitiesWithCountry);
    }

    public void updateCitiesInOperation(Map<Long, Boolean> updatedCities) {
        List<CityInOperation> currentCities = cityInOperationRepository.findAll();
        for (CityInOperation city : currentCities) {
            long cityId = city.getLocation().getEntityId();

            if (updatedCities.get(cityId)) {
                if (!city.isActivity()) {
                    city.setActivity(true);
                    city.setActivatingDate(LocalDateTime.now());
                    cityInOperationRepository.save(city);
                }
            } else {
                if (city.isActivity()) {
                    city.setActivity(false);
                    cityInOperationRepository.save(city);
                }
            }
        }

        List<Long> cityInOperationIds = currentCities.stream()
                .map(cityInOperation -> cityInOperation.getLocation().getEntityId())
                .collect(Collectors.toList());

        List<Long> newCitiesIds = updatedCities.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .filter(cityId -> !cityInOperationIds.contains(cityId))
                .collect(Collectors.toList());

        for (Long cityId : newCitiesIds) {
            CityInOperation newCityInOperation = new CityInOperation();
            newCityInOperation.setActivity(true);
            newCityInOperation.setActivatingDate(LocalDateTime.now());
            newCityInOperation.setAddDate(LocalDateTime.now());
            newCityInOperation.setLocation(locationRepository.findLocationByEntityId(cityId));
            cityInOperationRepository.save(newCityInOperation);
        }

        taskQueueRepository.setNeedToStartUpdateRoutes(true);
        updateCashedLocationsInOperation();
        updateCashedCitiesInOperation();
    }
}