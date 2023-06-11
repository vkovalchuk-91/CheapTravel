package org.geekhub.kovalchuk.service;

import org.geekhub.kovalchuk.model.dto.FavouritesDto;
import org.geekhub.kovalchuk.model.dto.FavouritesDto.RouteCities;
import org.geekhub.kovalchuk.model.dto.FavouritesDto.SearchLinkParameters;
import org.geekhub.kovalchuk.model.dto.SearchParametersDto;
import org.geekhub.kovalchuk.model.entity.Favourite;
import org.geekhub.kovalchuk.model.request.SearchParamsRequest;
import org.geekhub.kovalchuk.repository.jpa.FavouriteRepository;
import org.geekhub.kovalchuk.repository.jpa.LocationRepository;
import org.geekhub.kovalchuk.repository.jpa.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FavouriteService {
    private final LocationRepository locationRepository;
    private final FavouriteRepository favouriteRepository;
    private final UserRepository userRepository;

    public FavouriteService(LocationRepository locationRepository,
                            FavouriteRepository favouriteRepository,
                            UserRepository userRepository) {
        this.locationRepository = locationRepository;
        this.favouriteRepository = favouriteRepository;
        this.userRepository = userRepository;
    }

    public long addFavourite(String username, SearchParamsRequest searchParams) {
        Favourite favourite = new Favourite();

        favourite.setUser(userRepository.findByUsername(username));
        favourite.setFlightNumber(searchParams.getFlightNumber());
        favourite.setStartPoint1(locationRepository.findLocationByEntityId(searchParams.getStartPointsIds().get(0)));
        favourite.setEndPoint1(locationRepository.findLocationByEntityId(searchParams.getEndPointsIds().get(0)));
        favourite.setStartPoint2(locationRepository.findLocationByEntityId(searchParams.getStartPointsIds().get(1)));
        favourite.setEndPoint2(locationRepository.findLocationByEntityId(searchParams.getEndPointsIds().get(1)));
        if (searchParams.getFlightNumber() >= 3) {
            favourite.setStartPoint3(locationRepository.findLocationByEntityId(searchParams.getStartPointsIds().get(2)));
            favourite.setEndPoint3(locationRepository.findLocationByEntityId(searchParams.getEndPointsIds().get(2)));
        }
        if (searchParams.getFlightNumber() >= 4) {
            favourite.setStartPoint4(locationRepository.findLocationByEntityId(searchParams.getStartPointsIds().get(3)));
            favourite.setEndPoint4(locationRepository.findLocationByEntityId(searchParams.getEndPointsIds().get(3)));
        }

        favourite.setStartTripDate(searchParams.getStartTripDate());
        favourite.setEndTripDate(searchParams.getEndTripDate());
        favourite.setDaysInPointMin(searchParams.getDaysInPointMin());
        favourite.setDaysInPointMax(searchParams.getDaysInPointMax());
        favourite.setTotalDaysMin(searchParams.getTotalDaysMin());
        favourite.setTotalDaysMax(searchParams.getTotalDaysMax());
        favourite.setTotalCostMin(searchParams.getTotalCostMin());
        favourite.setTotalCostMax(searchParams.getTotalCostMax());

        favouriteRepository.save(favourite);
        return favourite.getId();
    }

    public long findTripInFavouritesId(String username, SearchParamsRequest searchParams) {
        int flightNumber = searchParams.getFlightNumber();
        List<Long> startPointsIds = searchParams.getStartPointsIds();
        List<Long> endPointsIds = searchParams.getEndPointsIds();
        Favourite tripSearchInFavourites = null;
        if (flightNumber == 2) {
            tripSearchInFavourites = favouriteRepository.findTripWith2FlightsInFavourites(
                    userRepository.findByUsername(username),
                    locationRepository.findLocationByEntityId(startPointsIds.get(0)),
                    locationRepository.findLocationByEntityId(endPointsIds.get(0)),
                    locationRepository.findLocationByEntityId(startPointsIds.get(1)),
                    locationRepository.findLocationByEntityId(endPointsIds.get(1)),
                    searchParams.getStartTripDate(),
                    searchParams.getEndTripDate(),
                    searchParams.getDaysInPointMin(),
                    searchParams.getDaysInPointMax(),
                    searchParams.getTotalDaysMin(),
                    searchParams.getTotalDaysMax(),
                    searchParams.getTotalCostMin(),
                    searchParams.getTotalCostMax());
        } else if (flightNumber == 3) {
            tripSearchInFavourites = favouriteRepository.findTripWith3FlightsInFavourites(
                    userRepository.findByUsername(username),
                    locationRepository.findLocationByEntityId(startPointsIds.get(0)),
                    locationRepository.findLocationByEntityId(endPointsIds.get(0)),
                    locationRepository.findLocationByEntityId(startPointsIds.get(1)),
                    locationRepository.findLocationByEntityId(endPointsIds.get(1)),
                    locationRepository.findLocationByEntityId(startPointsIds.get(2)),
                    locationRepository.findLocationByEntityId(endPointsIds.get(2)),
                    searchParams.getStartTripDate(),
                    searchParams.getEndTripDate(),
                    searchParams.getDaysInPointMin(),
                    searchParams.getDaysInPointMax(),
                    searchParams.getTotalDaysMin(),
                    searchParams.getTotalDaysMax(),
                    searchParams.getTotalCostMin(),
                    searchParams.getTotalCostMax());
        } else if (flightNumber == 4) {
            tripSearchInFavourites = favouriteRepository.findTripWith4FlightsInFavourites(
                    userRepository.findByUsername(username),
                    locationRepository.findLocationByEntityId(startPointsIds.get(0)),
                    locationRepository.findLocationByEntityId(endPointsIds.get(0)),
                    locationRepository.findLocationByEntityId(startPointsIds.get(1)),
                    locationRepository.findLocationByEntityId(endPointsIds.get(1)),
                    locationRepository.findLocationByEntityId(startPointsIds.get(2)),
                    locationRepository.findLocationByEntityId(endPointsIds.get(2)),
                    locationRepository.findLocationByEntityId(startPointsIds.get(3)),
                    locationRepository.findLocationByEntityId(endPointsIds.get(3)),
                    searchParams.getStartTripDate(),
                    searchParams.getEndTripDate(),
                    searchParams.getDaysInPointMin(),
                    searchParams.getDaysInPointMax(),
                    searchParams.getTotalDaysMin(),
                    searchParams.getTotalDaysMax(),
                    searchParams.getTotalCostMin(),
                    searchParams.getTotalCostMax()
            );
        }
        return tripSearchInFavourites != null ? tripSearchInFavourites.getId() : 0;
    }

    public void deleteFavourite(long favouriteId) {
        favouriteRepository.deleteById(favouriteId);
    }

    public FavouritesDto getFavourites(String username) {
        List<Favourite> favourites = favouriteRepository.findFavouriteByUser(userRepository.findByUsername(username));
        Map<List<RouteCities>, List<SearchLinkParameters>> favouritesRawData = new HashMap<>();

        for (Favourite favourite : favourites) {
            List<RouteCities> routeCitiesList = new ArrayList<>();
            routeCitiesList.add(new RouteCities(favourite.getStartPoint1().getName(),
                    favourite.getEndPoint1().getName()));
            routeCitiesList.add(new RouteCities(favourite.getStartPoint2().getName(),
                    favourite.getEndPoint2().getName()));
            if (favourite.getFlightNumber() >= 3) {
                routeCitiesList.add(new RouteCities(favourite.getStartPoint3().getName(),
                        favourite.getEndPoint3().getName()));
            }
            if (favourite.getFlightNumber() >= 4) {
                routeCitiesList.add(new RouteCities(favourite.getStartPoint4().getName(),
                        favourite.getEndPoint4().getName()));
            }

            SearchLinkParameters searchLinkParameters = new SearchLinkParameters(
                    favourite.getId(),
                    favourite.getStartTripDate(),
                    favourite.getEndTripDate(),
                    favourite.getDaysInPointMin(),
                    favourite.getDaysInPointMax(),
                    favourite.getTotalDaysMin(),
                    favourite.getTotalDaysMax(),
                    favourite.getTotalCostMin(),
                    favourite.getTotalCostMax());

            if (!favouritesRawData.containsKey(routeCitiesList)) {
                List<SearchLinkParameters> searchLinkParametersList = new ArrayList<>();
                searchLinkParametersList.add(searchLinkParameters);
                favouritesRawData.put(routeCitiesList, searchLinkParametersList);
            } else {
                favouritesRawData.get(routeCitiesList).add(searchLinkParameters);
            }
        }

        return new FavouritesDto(favouritesRawData);
    }

    public SearchParametersDto getSearchParameters(long favouriteId) {
        SearchParametersDto parameters;
        if (favouriteId == 0) {
            parameters = getDefaultParameters();
        } else {
            parameters = getFavouriteParameters(favouriteId);
        }
        return parameters;
    }

    private SearchParametersDto getDefaultParameters() {
        SearchParametersDto parameters = new SearchParametersDto();
        parameters.setShowResults(0);
        parameters.setFlightNumber(2);
        parameters.setStartTripDate(LocalDate.now());
        LocalDate nextMonth = LocalDate.now().plusMonths(1);
        parameters.setEndTripDate(nextMonth.withDayOfMonth(nextMonth.lengthOfMonth()));
        parameters.setDaysInPointMin(3);
        parameters.setDaysInPointMax(5);
        parameters.setTotalDaysMin(1);
        parameters.setTotalDaysMax(14);
        parameters.setTotalCostMin(1);
        parameters.setTotalCostMax(300);
        return parameters;
    }

    private SearchParametersDto getFavouriteParameters(long favouriteId) {
        SearchParametersDto parameters = new SearchParametersDto();
        Favourite favourite = favouriteRepository.getById(favouriteId);
        int flightNumber = favourite.getFlightNumber();

        parameters.setShowResults(1);
        parameters.setFlightNumber(flightNumber);
        parameters.setStartPointsId1(favourite.getStartPoint1().getEntityId());
        parameters.setEndPointsId1(favourite.getEndPoint1().getEntityId());
        parameters.setStartPointsId2(favourite.getStartPoint2().getEntityId());
        parameters.setEndPointsId2(favourite.getEndPoint2().getEntityId());
        if (flightNumber >= 3) {
            parameters.setStartPointsId3(favourite.getStartPoint3().getEntityId());
            parameters.setEndPointsId3(favourite.getEndPoint3().getEntityId());
        }
        if (flightNumber >= 4) {
            parameters.setStartPointsId4(favourite.getStartPoint4().getEntityId());
            parameters.setEndPointsId4(favourite.getEndPoint4().getEntityId());
        }

        parameters.setStartTripDate(favourite.getStartTripDate());
        parameters.setEndTripDate(favourite.getEndTripDate());
        parameters.setDaysInPointMin(favourite.getDaysInPointMin());
        parameters.setDaysInPointMax(favourite.getDaysInPointMax());
        parameters.setTotalDaysMin(favourite.getTotalDaysMin());
        parameters.setTotalDaysMax(favourite.getTotalDaysMax());
        parameters.setTotalCostMin(favourite.getTotalCostMin());
        parameters.setTotalCostMax(favourite.getTotalCostMax());
        return parameters;
    }
}