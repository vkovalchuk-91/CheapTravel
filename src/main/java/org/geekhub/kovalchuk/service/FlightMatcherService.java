package org.geekhub.kovalchuk.service;

import org.geekhub.kovalchuk.model.dto.TripSearchResultDto;
import org.geekhub.kovalchuk.model.entity.Location;
import org.geekhub.kovalchuk.model.request.SearchParamsRequest;
import org.geekhub.kovalchuk.repository.FlightMatcherRepository;
import org.geekhub.kovalchuk.repository.jpa.*;
import org.springframework.stereotype.Service;

@Service
public class FlightMatcherService {
    private final LocationRepository locationRepository;
    private final FlightMatcherRepository flightMatcherRepository;
    private final FavouriteService favouriteService;
    private static final String TYPE_CITY = "PLACE_TYPE_CITY";

    public FlightMatcherService(LocationRepository locationRepository,
                                FlightMatcherRepository flightMatcherRepository,
                                FavouriteService favouriteService) {
        this.locationRepository = locationRepository;
        this.flightMatcherRepository = flightMatcherRepository;
        this.favouriteService = favouriteService;
    }


    public TripSearchResultDto getMatchedFlights(String username, SearchParamsRequest searchParams) {
        TripSearchResultDto flightMatcherDto = null;

        int flightsNumber = searchParams.getStartPointsIds().size();
        switch (flightsNumber) {
            case 2:
                flightMatcherDto = flightMatcherRepository.getTripWith2Flights(searchParams,
                        getFavouriteId(username, searchParams),
                        isPointCity(searchParams.getStartPointsIds().get(0)),
                        isPointCity(searchParams.getEndPointsIds().get(0)),
                        isPointCity(searchParams.getStartPointsIds().get(1)),
                        isPointCity(searchParams.getEndPointsIds().get(1)));
                break;
            case 3:
                flightMatcherDto = flightMatcherRepository.getTripWith3Flights(searchParams,
                        getFavouriteId(username, searchParams),
                        isPointCity(searchParams.getStartPointsIds().get(0)),
                        isPointCity(searchParams.getEndPointsIds().get(0)),
                        isPointCity(searchParams.getStartPointsIds().get(1)),
                        isPointCity(searchParams.getEndPointsIds().get(1)),
                        isPointCity(searchParams.getStartPointsIds().get(2)),
                        isPointCity(searchParams.getEndPointsIds().get(2)));
                break;
            case 4:
                flightMatcherDto = flightMatcherRepository.getTripWith4Flights(searchParams,
                        getFavouriteId(username, searchParams),
                        isPointCity(searchParams.getStartPointsIds().get(0)),
                        isPointCity(searchParams.getEndPointsIds().get(0)),
                        isPointCity(searchParams.getStartPointsIds().get(1)),
                        isPointCity(searchParams.getEndPointsIds().get(1)),
                        isPointCity(searchParams.getStartPointsIds().get(2)),
                        isPointCity(searchParams.getEndPointsIds().get(2)),
                        isPointCity(searchParams.getStartPointsIds().get(3)),
                        isPointCity(searchParams.getEndPointsIds().get(3)));
                break;
        }
        return flightMatcherDto;
    }

    private long getFavouriteId(String username, SearchParamsRequest searchParams) {
        return favouriteService.findTripInFavouritesId(username, searchParams);
    }

    private boolean isPointCity(Long locationId) {
        Location location = locationRepository.findLocationByEntityId(locationId);
        String locationName = location.getLocationType().getName();
        return locationName.equals(TYPE_CITY);
    }
}