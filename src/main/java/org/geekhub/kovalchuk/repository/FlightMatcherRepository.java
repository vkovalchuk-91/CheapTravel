package org.geekhub.kovalchuk.repository;

import org.geekhub.kovalchuk.model.dto.TripSearchResultDto;
import org.geekhub.kovalchuk.model.request.SearchParamsRequest;

public interface FlightMatcherRepository {
    TripSearchResultDto getTripWith2Flights(SearchParamsRequest searchParams,
                                            long favouriteId,
                                            boolean isFirstStartPointCity,
                                            boolean isFirstEndPointCity,
                                            boolean isSecondStartPointCity,
                                            boolean isSecondEndPointCity);
    TripSearchResultDto getTripWith3Flights(SearchParamsRequest searchParams,
                                            long favouriteId,
                                            boolean isFirstStartPointCity,
                                            boolean isFirstEndPointCity,
                                            boolean isSecondStartPointCity,
                                            boolean isSecondEndPointCity,
                                            boolean isThirdStartPointCity,
                                            boolean isThirdEndPointCity);
    TripSearchResultDto getTripWith4Flights(SearchParamsRequest searchParams,
                                            long favouriteId,
                                            boolean isFirstStartPointCity,
                                            boolean isFirstEndPointCity,
                                            boolean isSecondStartPointCity,
                                            boolean isSecondEndPointCity,
                                            boolean isThirdStartPointCity,
                                            boolean isThirdEndPointCity,
                                            boolean isFourthStartPointCity,
                                            boolean isFourthEndPointCity);
}
