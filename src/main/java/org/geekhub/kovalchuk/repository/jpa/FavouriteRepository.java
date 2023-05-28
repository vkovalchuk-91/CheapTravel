package org.geekhub.kovalchuk.repository.jpa;

import org.geekhub.kovalchuk.model.entity.Favourite;
import org.geekhub.kovalchuk.model.entity.Location;
import org.geekhub.kovalchuk.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    List<Favourite> findFavouriteByUser(User user);

    @Query("SELECT fav " +
            "FROM Favourite fav " +
            "WHERE fav.flightNumber = 2 " +
            "AND fav.user = :user " +
            "AND fav.startPoint1 = :startPoint1 " +
            "AND fav.endPoint1 = :endPoint1 " +
            "AND fav.startPoint2 = :startPoint2 " +
            "AND fav.endPoint2 = :endPoint2 " +
            "AND fav.startTripDate = :startTripDate " +
            "AND fav.endTripDate = :endTripDate " +
            "AND fav.daysInPointMin = :daysInPointMin " +
            "AND fav.daysInPointMax = :daysInPointMax " +
            "AND fav.totalDaysMin = :totalDaysMin " +
            "AND fav.totalDaysMax = :totalDaysMax " +
            "AND fav.totalCostMin = :totalCostMin " +
            "AND fav.totalCostMax = :totalCostMax"
    )
    Favourite findTripWith2FlightsInFavourites(@Param("user") User user,
                                               @Param("startPoint1") Location startPoint1,
                                               @Param("endPoint1") Location endPoint1,
                                               @Param("startPoint2") Location startPoint2,
                                               @Param("endPoint2") Location endPoint2,
                                               @Param("startTripDate") LocalDate startTripDate,
                                               @Param("endTripDate") LocalDate endTripDate,
                                               @Param("daysInPointMin") int daysInPointMin,
                                               @Param("daysInPointMax") int daysInPointMax,
                                               @Param("totalDaysMin") int totalDaysMin,
                                               @Param("totalDaysMax") int totalDaysMax,
                                               @Param("totalCostMin") int totalCostMin,
                                               @Param("totalCostMax") int totalCostMax);


    @Query("SELECT fav " +
            "FROM Favourite fav " +
            "WHERE fav.flightNumber = 3 " +
            "AND fav.user = :user " +
            "AND fav.startPoint1 = :startPoint1 " +
            "AND fav.endPoint1 = :endPoint1 " +
            "AND fav.startPoint2 = :startPoint2 " +
            "AND fav.endPoint2 = :endPoint2 " +
            "AND fav.startPoint3 = :startPoint3 " +
            "AND fav.endPoint3 = :endPoint3 " +
            "AND fav.startTripDate = :startTripDate " +
            "AND fav.endTripDate = :endTripDate " +
            "AND fav.daysInPointMin = :daysInPointMin " +
            "AND fav.daysInPointMax = :daysInPointMax " +
            "AND fav.totalDaysMin = :totalDaysMin " +
            "AND fav.totalDaysMax = :totalDaysMax " +
            "AND fav.totalCostMin = :totalCostMin " +
            "AND fav.totalCostMax = :totalCostMax"
    )
    Favourite findTripWith3FlightsInFavourites(@Param("user") User user,
                                               @Param("startPoint1") Location startPoint1,
                                               @Param("endPoint1") Location endPoint1,
                                               @Param("startPoint2") Location startPoint2,
                                               @Param("endPoint2") Location endPoint2,
                                               @Param("startPoint3") Location startPoint3,
                                               @Param("endPoint3") Location endPoint3,
                                               @Param("startTripDate") LocalDate startTripDate,
                                               @Param("endTripDate") LocalDate endTripDate,
                                               @Param("daysInPointMin") int daysInPointMin,
                                               @Param("daysInPointMax") int daysInPointMax,
                                               @Param("totalDaysMin") int totalDaysMin,
                                               @Param("totalDaysMax") int totalDaysMax,
                                               @Param("totalCostMin") int totalCostMin,
                                               @Param("totalCostMax") int totalCostMax);

    @Query("SELECT fav " +
            "FROM Favourite fav " +
            "WHERE fav.flightNumber = 4 " +
            "AND fav.user = :user " +
            "AND fav.startPoint1 = :startPoint1 " +
            "AND fav.endPoint1 = :endPoint1 " +
            "AND fav.startPoint2 = :startPoint2 " +
            "AND fav.endPoint2 = :endPoint2 " +
            "AND fav.startPoint3 = :startPoint3 " +
            "AND fav.endPoint3 = :endPoint3 " +
            "AND fav.startPoint4 = :startPoint4 " +
            "AND fav.endPoint4 = :endPoint4 " +
            "AND fav.startTripDate = :startTripDate " +
            "AND fav.endTripDate = :endTripDate " +
            "AND fav.daysInPointMin = :daysInPointMin " +
            "AND fav.daysInPointMax = :daysInPointMax " +
            "AND fav.totalDaysMin = :totalDaysMin " +
            "AND fav.totalDaysMax = :totalDaysMax " +
            "AND fav.totalCostMin = :totalCostMin " +
            "AND fav.totalCostMax = :totalCostMax"
    )
    Favourite findTripWith4FlightsInFavourites(@Param("user") User user,
                                               @Param("startPoint1") Location startPoint1,
                                               @Param("endPoint1") Location endPoint1,
                                               @Param("startPoint2") Location startPoint2,
                                               @Param("endPoint2") Location endPoint2,
                                               @Param("startPoint3") Location startPoint3,
                                               @Param("endPoint3") Location endPoint3,
                                               @Param("startPoint4") Location startPoint4,
                                               @Param("endPoint4") Location endPoint4,
                                               @Param("startTripDate") LocalDate startTripDate,
                                               @Param("endTripDate") LocalDate endTripDate,
                                               @Param("daysInPointMin") int daysInPointMin,
                                               @Param("daysInPointMax") int daysInPointMax,
                                               @Param("totalDaysMin") int totalDaysMin,
                                               @Param("totalDaysMax") int totalDaysMax,
                                               @Param("totalCostMin") int totalCostMin,
                                               @Param("totalCostMax") int totalCostMax);
}
