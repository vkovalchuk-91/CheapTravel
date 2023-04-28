package org.geekhub.kovalchuk.service;

import org.geekhub.kovalchuk.config.PropertiesConfig;
import org.geekhub.kovalchuk.model.entity.CityInOperation;
import org.geekhub.kovalchuk.model.entity.Location;
import org.geekhub.kovalchuk.model.dto.LocationInOperationDto;
import org.geekhub.kovalchuk.repository.CityInOperationRepository;
import org.geekhub.kovalchuk.repository.LocationRepository;
import org.geekhub.kovalchuk.repository.LocationTypeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CityInOperationService {
    public static final String TYPE_COUNTRY = "PLACE_TYPE_COUNTRY";
    CityInOperationRepository cityInOperationRepository;
    LocationRepository locationRepository;
    LocationTypeRepository locationTypeRepository;
    PropertiesConfig properties;
    private List<LocationInOperationDto> cashedLocationsInOperation = new ArrayList<>();

    public CityInOperationService(CityInOperationRepository cityInOperationRepository,
                                  LocationRepository locationRepository,
                                  LocationTypeRepository locationTypeRepository,
                                  PropertiesConfig properties) {
        this.cityInOperationRepository = cityInOperationRepository;
        this.locationRepository = locationRepository;
        this.locationTypeRepository = locationTypeRepository;
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
            refreshCashedLocationsInOperation();
        }
        return cashedLocationsInOperation;
    }

    public void refreshCashedLocationsInOperation() {
        List<Location> citiesInOperation = cityInOperationRepository.findAll().stream()
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
}
