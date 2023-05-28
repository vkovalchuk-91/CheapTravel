package org.geekhub.kovalchuk.repository.implementation;

import org.geekhub.kovalchuk.model.dto.TripSearchResultDto;
import org.geekhub.kovalchuk.model.dto.TripSearchResultDto.Trip;
import org.geekhub.kovalchuk.model.dto.TripSearchResultDto.Trip.FlightInfo;
import org.geekhub.kovalchuk.model.request.SearchParamsRequest;
import org.geekhub.kovalchuk.repository.FlightMatcherRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightMatcherRepositoryImpl implements FlightMatcherRepository {
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public FlightMatcherRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public TripSearchResultDto getTripWith2Flights(SearchParamsRequest searchParams,
                                                   long favouriteId,
                                                   boolean isFirstStartPointCity,
                                                   boolean isFirstEndPointCity,
                                                   boolean isSecondStartPointCity,
                                                   boolean isSecondEndPointCity) {
        String route1IdsQuery = getRouteIdsQuery(
                searchParams.getStartPointsIds().get(0),
                isFirstStartPointCity,
                searchParams.getEndPointsIds().get(0),
                isFirstEndPointCity);

        String route2IdsQuery = getRouteIdsQuery(
                searchParams.getStartPointsIds().get(1),
                isSecondStartPointCity,
                searchParams.getEndPointsIds().get(1),
                isSecondEndPointCity);

        String sql = "SELECT l_from_1.name        AS from_city_1, " +
                "       l_to_1.name          AS to_city_1, " +
                "       l_to_1.name          AS to_city_1, " +
                "       l_from_1.sky_scanner_abbreviation          AS from_city_abbr_1, " +
                "       l_to_1.sky_scanner_abbreviation            AS to_city_abbr_1, " +
                "       search_result.date_1 AS date_1, " +
                "       l_from_2.name        AS from_city_2, " +
                "       l_to_2.name          AS to_city_2, " +
                "       l_from_2.sky_scanner_abbreviation          AS from_city_abbr_2, " +
                "       l_to_2.sky_scanner_abbreviation            AS to_city_abbr_2, " +
                "       search_result.date_2 AS date_2, " +
                "       search_result.sum    AS total_cost " +
                "FROM (SELECT f1.route_id                      AS r1, " +
                "             f1.flight_date                   AS date_1, " +
                "             f2.route_id                      AS r2, " +
                "             f2.flight_date                   AS date_2, " +
                "             (f1.price + f2.price) AS sum " +
                "      FROM flight f1 " +
                "               JOIN flight f2 ON f2.flight_date >= " +
                "                           (f1.flight_date + CAST(:daysInPointMin || ' days' AS INTERVAL)) " +
                "          AND f2.flight_date <= " +
                "                           (f1.flight_date + CAST(:daysInPointMax || ' days' AS INTERVAL)) " +
                "      WHERE f1.route_id IN (" + route1IdsQuery + ") " +
                "        AND f2.route_id IN (" + route2IdsQuery + ") " +
                "        AND f1.flight_date >= :startTripDate " +
                "        AND f1.flight_date <= :endTripDate " +
                "        AND f2.flight_date >= :startTripDate " +
                "        AND f2.flight_date <= :endTripDate " +
                "        AND (f2.flight_date - f1.flight_date) >= :totalDaysMin " +
                "        AND (f2.flight_date - f1.flight_date) <= :totalDaysMax " +
                "        AND (f1.price + f2.price) >= :totalCostMin " +
                "        AND (f1.price + f2.price) <= :totalCostMax) AS search_result " +

                "         JOIN route r1 ON search_result.r1 = r1.id " +
                "         JOIN location l_from_1 ON l_from_1.entity_id = r1.from_city_id " +
                "         JOIN location l_to_1 ON l_to_1.entity_id = r1.to_city_id " +

                "         JOIN route r2 ON search_result.r2 = r2.id " +
                "         JOIN location l_from_2 ON l_from_2.entity_id = r2.from_city_id " +
                "         JOIN location l_to_2 ON l_to_2.entity_id = r2.to_city_id ";

        if (searchParams.isLowPriceSwitcher()) {
            sql += "ORDER BY sum";
        }

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("daysInPointMin", searchParams.getDaysInPointMin());
        parameters.addValue("daysInPointMax", searchParams.getDaysInPointMax());
        parameters.addValue("startTripDate", searchParams.getStartTripDate());
        parameters.addValue("endTripDate", searchParams.getEndTripDate());
        parameters.addValue("totalDaysMin", searchParams.getTotalDaysMin());
        parameters.addValue("totalDaysMax", searchParams.getTotalDaysMax());
        parameters.addValue("totalCostMin", searchParams.getTotalCostMin());
        parameters.addValue("totalCostMax", searchParams.getTotalCostMax());

        return namedParameterJdbcTemplate.query(sql,
                parameters,
                rs -> {
                    List<Trip> routes = new ArrayList<>();
                    while (rs.next()) {
                        String fromCity1 = rs.getString("from_city_1");
                        String toCity1 = rs.getString("to_city_1");
                        String fromCityAbbr1 = rs.getString("from_city_abbr_1");
                        String toCityAbbr1 = rs.getString("to_city_abbr_1");
                        LocalDate date1 = rs.getDate("date_1").toLocalDate();
                        String fromCity2 = rs.getString("from_city_2");
                        String toCity2 = rs.getString("to_city_2");
                        String fromCityAbbr2 = rs.getString("from_city_abbr_2");
                        String toCityAbbr2 = rs.getString("to_city_abbr_2");
                        LocalDate date2 = rs.getDate("date_2").toLocalDate();
                        double totalCost = rs.getDouble("total_cost");

                        List<FlightInfo> flights = new ArrayList<>();
                        flights.add(new FlightInfo(fromCity1, toCity1, date1));
                        flights.add(new FlightInfo(fromCity2, toCity2, date2));

                        List<String[]> abbreviations = new ArrayList<>();
                        abbreviations.add(new String[]{fromCityAbbr1, toCityAbbr1});
                        abbreviations.add(new String[]{fromCityAbbr2, toCityAbbr2});

                        routes.add(new Trip(flights, totalCost, abbreviations));
                    }
                    return new TripSearchResultDto(routes, favouriteId);
                });
    }

    @Override
    public TripSearchResultDto getTripWith3Flights(SearchParamsRequest searchParams,
                                                   long favouriteId,
                                                   boolean isFirstStartPointCity,
                                                   boolean isFirstEndPointCity,
                                                   boolean isSecondStartPointCity,
                                                   boolean isSecondEndPointCity,
                                                   boolean isThirdStartPointCity,
                                                   boolean isThirdEndPointCity) {
        String route1IdsQuery = getRouteIdsQuery(
                searchParams.getStartPointsIds().get(0),
                isFirstStartPointCity,
                searchParams.getEndPointsIds().get(0),
                isFirstEndPointCity);

        String route2IdsQuery = getRouteIdsQuery(
                searchParams.getStartPointsIds().get(1),
                isSecondStartPointCity,
                searchParams.getEndPointsIds().get(1),
                isSecondEndPointCity);

        String route3IdsQuery = getRouteIdsQuery(
                searchParams.getStartPointsIds().get(2),
                isThirdStartPointCity,
                searchParams.getEndPointsIds().get(2),
                isThirdEndPointCity);

        String sql = "SELECT l_from_1.name        AS from_city_1, " +
                "       l_to_1.name          AS to_city_1, " +
                "       l_from_1.sky_scanner_abbreviation          AS from_city_abbr_1, " +
                "       l_to_1.sky_scanner_abbreviation            AS to_city_abbr_1, " +
                "       search_result.date_1 AS date_1, " +
                "       l_from_2.name        AS from_city_2, " +
                "       l_to_2.name          AS to_city_2, " +
                "       l_from_2.sky_scanner_abbreviation          AS from_city_abbr_2, " +
                "       l_to_2.sky_scanner_abbreviation            AS to_city_abbr_2, " +
                "       search_result.date_2 AS date_2, " +
                "       l_from_3.name        AS from_city_3, " +
                "       l_to_3.name          AS to_city_3, " +
                "       l_from_3.sky_scanner_abbreviation          AS from_city_abbr_3, " +
                "       l_to_3.sky_scanner_abbreviation            AS to_city_abbr_3, " +
                "       search_result.date_3 AS date_3, " +
                "       search_result.sum    AS total_cost " +
                "FROM (SELECT f1.route_id                      AS r1, " +
                "             f1.flight_date                   AS date_1, " +
                "             f2.route_id                      AS r2, " +
                "             f2.flight_date                   AS date_2, " +
                "             f3.route_id                      AS r3, " +
                "             f3.flight_date                   AS date_3, " +
                "             (f1.price + f2.price + f3.price) AS sum " +
                "      FROM flight f1 " +
                "               JOIN flight f2 ON f2.flight_date >= " +
                "                           (f1.flight_date + CAST(:daysInPointMin || ' days' AS INTERVAL)) " +
                "          AND f2.flight_date <= " +
                "                           (f1.flight_date + CAST(:daysInPointMax || ' days' AS INTERVAL)) " +
                "               JOIN flight f3 ON f3.flight_date >= " +
                "                           (f2.flight_date + CAST(:daysInPointMin || ' days' AS INTERVAL)) " +
                "          AND f3.flight_date <= " +
                "                           (f2.flight_date + CAST(:daysInPointMax || ' days' AS INTERVAL)) " +
                "      WHERE f1.route_id IN (" + route1IdsQuery + ") " +
                "        AND f2.route_id IN (" + route2IdsQuery + ") " +
                "        AND f3.route_id IN (" + route3IdsQuery + ") " +
                "        AND f1.flight_date >= :startTripDate " +
                "        AND f1.flight_date <= :endTripDate " +
                "        AND f2.flight_date >= :startTripDate " +
                "        AND f2.flight_date <= :endTripDate " +
                "        AND f3.flight_date >= :startTripDate " +
                "        AND f3.flight_date <= :endTripDate " +
                "        AND (f3.flight_date - f1.flight_date) >= :totalDaysMin " +
                "        AND (f3.flight_date - f1.flight_date) <= :totalDaysMax " +
                "        AND (f1.price + f2.price + f3.price) >= :totalCostMin " +
                "        AND (f1.price + f2.price + f3.price) <= :totalCostMax) AS search_result " +

                "         JOIN route r1 ON search_result.r1 = r1.id " +
                "         JOIN location l_from_1 ON l_from_1.entity_id = r1.from_city_id " +
                "         JOIN location l_to_1 ON l_to_1.entity_id = r1.to_city_id " +

                "         JOIN route r2 ON search_result.r2 = r2.id " +
                "         JOIN location l_from_2 ON l_from_2.entity_id = r2.from_city_id " +
                "         JOIN location l_to_2 ON l_to_2.entity_id = r2.to_city_id " +

                "         JOIN route r3 ON search_result.r3 = r3.id " +
                "         JOIN location l_from_3 ON l_from_3.entity_id = r3.from_city_id " +
                "         JOIN location l_to_3 ON l_to_3.entity_id = r3.to_city_id ";

        if (searchParams.isLowPriceSwitcher()) {
            sql += "ORDER BY sum";
        }

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("daysInPointMin", searchParams.getDaysInPointMin());
        parameters.addValue("daysInPointMax", searchParams.getDaysInPointMax());
        parameters.addValue("startTripDate", searchParams.getStartTripDate());
        parameters.addValue("endTripDate", searchParams.getEndTripDate());
        parameters.addValue("totalDaysMin", searchParams.getTotalDaysMin());
        parameters.addValue("totalDaysMax", searchParams.getTotalDaysMax());
        parameters.addValue("totalCostMin", searchParams.getTotalCostMin());
        parameters.addValue("totalCostMax", searchParams.getTotalCostMax());

        return namedParameterJdbcTemplate.query(sql,
                parameters,
                rs -> {
                    List<Trip> routes = new ArrayList<>();
                    while (rs.next()) {
                        String fromCity1 = rs.getString("from_city_1");
                        String toCity1 = rs.getString("to_city_1");
                        String fromCityAbbr1 = rs.getString("from_city_abbr_1");
                        String toCityAbbr1 = rs.getString("to_city_abbr_1");
                        LocalDate date1 = rs.getDate("date_1").toLocalDate();
                        String fromCity2 = rs.getString("from_city_2");
                        String toCity2 = rs.getString("to_city_2");
                        String fromCityAbbr2 = rs.getString("from_city_abbr_2");
                        String toCityAbbr2 = rs.getString("to_city_abbr_2");
                        LocalDate date2 = rs.getDate("date_2").toLocalDate();
                        String fromCity3 = rs.getString("from_city_3");
                        String toCity3 = rs.getString("to_city_3");
                        String fromCityAbbr3 = rs.getString("from_city_abbr_3");
                        String toCityAbbr3 = rs.getString("to_city_abbr_3");
                        LocalDate date3 = rs.getDate("date_3").toLocalDate();
                        double totalCost = rs.getDouble("total_cost");

                        List<FlightInfo> flights = new ArrayList<>();
                        flights.add(new FlightInfo(fromCity1, toCity1, date1));
                        flights.add(new FlightInfo(fromCity2, toCity2, date2));
                        flights.add(new FlightInfo(fromCity3, toCity3, date3));

                        List<String[]> abbreviations = new ArrayList<>();
                        abbreviations.add(new String[]{fromCityAbbr1, toCityAbbr1});
                        abbreviations.add(new String[]{fromCityAbbr2, toCityAbbr2});
                        abbreviations.add(new String[]{fromCityAbbr3, toCityAbbr3});

                        routes.add(new Trip(flights, totalCost, abbreviations));
                    }
                    return new TripSearchResultDto(routes, favouriteId);
                });
    }

    @Override
    public TripSearchResultDto getTripWith4Flights(SearchParamsRequest searchParams,
                                                   long favouriteId,
                                                   boolean isFirstStartPointCity,
                                                   boolean isFirstEndPointCity,
                                                   boolean isSecondStartPointCity,
                                                   boolean isSecondEndPointCity,
                                                   boolean isThirdStartPointCity,
                                                   boolean isThirdEndPointCity,
                                                   boolean isFourthStartPointCity,
                                                   boolean isFourthEndPointCity) {
        String route1IdsQuery = getRouteIdsQuery(
                searchParams.getStartPointsIds().get(0),
                isFirstStartPointCity,
                searchParams.getEndPointsIds().get(0),
                isFirstEndPointCity);

        String route2IdsQuery = getRouteIdsQuery(
                searchParams.getStartPointsIds().get(1),
                isSecondStartPointCity,
                searchParams.getEndPointsIds().get(1),
                isSecondEndPointCity);

        String route3IdsQuery = getRouteIdsQuery(
                searchParams.getStartPointsIds().get(2),
                isThirdStartPointCity,
                searchParams.getEndPointsIds().get(2),
                isThirdEndPointCity);

        String route4IdsQuery = getRouteIdsQuery(
                searchParams.getStartPointsIds().get(3),
                isFourthStartPointCity,
                searchParams.getEndPointsIds().get(3),
                isFourthEndPointCity);

        String sql = "SELECT l_from_1.name        AS from_city_1, " +
                "       l_to_1.name          AS to_city_1, " +
                "       l_from_1.sky_scanner_abbreviation          AS from_city_abbr_1, " +
                "       l_to_1.sky_scanner_abbreviation            AS to_city_abbr_1, " +
                "       search_result.date_1 AS date_1, " +
                "       l_from_2.name        AS from_city_2, " +
                "       l_to_2.name          AS to_city_2, " +
                "       l_from_2.sky_scanner_abbreviation          AS from_city_abbr_2, " +
                "       l_to_2.sky_scanner_abbreviation            AS to_city_abbr_2, " +
                "       search_result.date_2 AS date_2, " +
                "       l_from_3.name        AS from_city_3, " +
                "       l_to_3.name          AS to_city_3, " +
                "       l_from_3.sky_scanner_abbreviation          AS from_city_abbr_3, " +
                "       l_to_3.sky_scanner_abbreviation            AS to_city_abbr_3, " +
                "       search_result.date_3 AS date_3, " +
                "       l_from_4.name        AS from_city_4, " +
                "       l_to_4.name          AS to_city_4, " +
                "       l_from_4.sky_scanner_abbreviation          AS from_city_abbr_4, " +
                "       l_to_4.sky_scanner_abbreviation            AS to_city_abbr_4, " +
                "       search_result.date_4 AS date_4, " +
                "       search_result.sum    AS total_cost " +
                "FROM (SELECT f1.route_id                      AS r1, " +
                "             f1.flight_date                   AS date_1, " +
                "             f2.route_id                      AS r2, " +
                "             f2.flight_date                   AS date_2, " +
                "             f3.route_id                      AS r3, " +
                "             f3.flight_date                   AS date_3, " +
                "             f4.route_id                      AS r4, " +
                "             f4.flight_date                   AS date_4, " +
                "             (f1.price + f2.price + f3.price + f4.price) AS sum " +
                "      FROM flight f1 " +
                "               JOIN flight f2 ON f2.flight_date >= " +
                "                           (f1.flight_date + CAST(:daysInPointMin || ' days' AS INTERVAL)) " +
                "          AND f2.flight_date <= " +
                "                           (f1.flight_date + CAST(:daysInPointMax || ' days' AS INTERVAL)) " +
                "               JOIN flight f3 ON f3.flight_date >= " +
                "                           (f2.flight_date + CAST(:daysInPointMin || ' days' AS INTERVAL)) " +
                "          AND f3.flight_date <= " +
                "                           (f2.flight_date + CAST(:daysInPointMax || ' days' AS INTERVAL)) " +
                "               JOIN flight f4 ON f4.flight_date >= " +
                "                           (f3.flight_date + CAST(:daysInPointMin || ' days' AS INTERVAL)) " +
                "          AND f4.flight_date <= " +
                "                           (f3.flight_date + CAST(:daysInPointMax || ' days' AS INTERVAL)) " +
                "      WHERE f1.route_id IN (" + route1IdsQuery + ") " +
                "        AND f2.route_id IN (" + route2IdsQuery + ") " +
                "        AND f3.route_id IN (" + route3IdsQuery + ") " +
                "        AND f4.route_id IN (" + route4IdsQuery + ") " +
                "        AND f1.flight_date >= :startTripDate " +
                "        AND f1.flight_date <= :endTripDate " +
                "        AND f2.flight_date >= :startTripDate " +
                "        AND f2.flight_date <= :endTripDate " +
                "        AND f3.flight_date >= :startTripDate " +
                "        AND f3.flight_date <= :endTripDate " +
                "        AND f4.flight_date >= :startTripDate " +
                "        AND f4.flight_date <= :endTripDate " +
                "        AND (f4.flight_date - f1.flight_date) >= :totalDaysMin " +
                "        AND (f4.flight_date - f1.flight_date) <= :totalDaysMax " +
                "        AND (f1.price + f2.price + f3.price + f4.price) >= :totalCostMin " +
                "        AND (f1.price + f2.price + f3.price + f4.price) <= :totalCostMax) AS search_result " +

                "         JOIN route r1 ON search_result.r1 = r1.id " +
                "         JOIN location l_from_1 ON l_from_1.entity_id = r1.from_city_id " +
                "         JOIN location l_to_1 ON l_to_1.entity_id = r1.to_city_id " +

                "         JOIN route r2 ON search_result.r2 = r2.id " +
                "         JOIN location l_from_2 ON l_from_2.entity_id = r2.from_city_id " +
                "         JOIN location l_to_2 ON l_to_2.entity_id = r2.to_city_id " +

                "         JOIN route r3 ON search_result.r3 = r3.id " +
                "         JOIN location l_from_3 ON l_from_3.entity_id = r3.from_city_id " +
                "         JOIN location l_to_3 ON l_to_3.entity_id = r3.to_city_id " +

                "         JOIN route r4 ON search_result.r4 = r4.id " +
                "         JOIN location l_from_4 ON l_from_4.entity_id = r4.from_city_id " +
                "         JOIN location l_to_4 ON l_to_4.entity_id = r4.to_city_id ";

        if (searchParams.isLowPriceSwitcher()) {
            sql += "ORDER BY sum";
        }

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("daysInPointMin", searchParams.getDaysInPointMin());
        parameters.addValue("daysInPointMax", searchParams.getDaysInPointMax());
        parameters.addValue("startTripDate", searchParams.getStartTripDate());
        parameters.addValue("endTripDate", searchParams.getEndTripDate());
        parameters.addValue("totalDaysMin", searchParams.getTotalDaysMin());
        parameters.addValue("totalDaysMax", searchParams.getTotalDaysMax());
        parameters.addValue("totalCostMin", searchParams.getTotalCostMin());
        parameters.addValue("totalCostMax", searchParams.getTotalCostMax());

        return namedParameterJdbcTemplate.query(sql,
                parameters,
                rs -> {
                    List<Trip> routes = new ArrayList<>();
                    while (rs.next()) {
                        String fromCity1 = rs.getString("from_city_1");
                        String toCity1 = rs.getString("to_city_1");
                        String fromCityAbbr1 = rs.getString("from_city_abbr_1");
                        String toCityAbbr1 = rs.getString("to_city_abbr_1");
                        LocalDate date1 = rs.getDate("date_1").toLocalDate();
                        String fromCity2 = rs.getString("from_city_2");
                        String toCity2 = rs.getString("to_city_2");
                        String fromCityAbbr2 = rs.getString("from_city_abbr_2");
                        String toCityAbbr2 = rs.getString("to_city_abbr_2");
                        LocalDate date2 = rs.getDate("date_2").toLocalDate();
                        String fromCity3 = rs.getString("from_city_3");
                        String toCity3 = rs.getString("to_city_3");
                        String fromCityAbbr3 = rs.getString("from_city_abbr_3");
                        String toCityAbbr3 = rs.getString("to_city_abbr_3");
                        LocalDate date3 = rs.getDate("date_3").toLocalDate();
                        String fromCity4 = rs.getString("from_city_4");
                        String toCity4 = rs.getString("to_city_4");
                        String fromCityAbbr4 = rs.getString("from_city_abbr_4");
                        String toCityAbbr4 = rs.getString("to_city_abbr_4");
                        LocalDate date4 = rs.getDate("date_4").toLocalDate();
                        double totalCost = rs.getDouble("total_cost");

                        List<FlightInfo> flights = new ArrayList<>();
                        flights.add(new FlightInfo(fromCity1, toCity1, date1));
                        flights.add(new FlightInfo(fromCity2, toCity2, date2));
                        flights.add(new FlightInfo(fromCity3, toCity3, date3));
                        flights.add(new FlightInfo(fromCity4, toCity4, date4));

                        List<String[]> abbreviations = new ArrayList<>();
                        abbreviations.add(new String[]{fromCityAbbr1, toCityAbbr1});
                        abbreviations.add(new String[]{fromCityAbbr2, toCityAbbr2});
                        abbreviations.add(new String[]{fromCityAbbr3, toCityAbbr3});
                        abbreviations.add(new String[]{fromCityAbbr4, toCityAbbr4});

                        routes.add(new Trip(flights, totalCost, abbreviations));
                    }
                    return new TripSearchResultDto(routes, favouriteId);
                });
    }

    private String getRouteIdsQuery(Long StartPointId,
                                    boolean isStartPointCity,
                                    Long EndPointId,
                                    boolean isEndPointCity) {
        StringBuilder sb = new StringBuilder();
        if (isStartPointCity) {
            if (isEndPointCity) {
                sb.append("SELECT id ");
                sb.append("FROM route ");
                sb.append("WHERE from_city_id = ");
                sb.append(StartPointId);
                sb.append(" ");
                sb.append("AND to_city_id = ");
                sb.append(EndPointId);
            } else {
                sb.append("SELECT id ");
                sb.append("FROM route ");
                sb.append("         JOIN location l on l.entity_id = route.to_city_id ");
                sb.append("WHERE from_city_id = ");
                sb.append(StartPointId);
                sb.append(" ");
                sb.append("  AND l.parent_id = ");
                sb.append(EndPointId);
            }
        } else {
            if (isEndPointCity) {
                sb.append("SELECT id ");
                sb.append("FROM route ");
                sb.append("         JOIN location l on l.entity_id = route.from_city_id ");
                sb.append("WHERE l.parent_id = ");
                sb.append(StartPointId);
                sb.append(" ");
                sb.append("  AND to_city_id = ");
                sb.append(EndPointId);
            } else {
                sb.append("SELECT id ");
                sb.append("FROM route ");
                sb.append("         JOIN location l1 on l1.entity_id = route.from_city_id ");
                sb.append("         JOIN location l2 on l2.entity_id = route.to_city_id ");
                sb.append("WHERE l1.parent_id = ");
                sb.append(StartPointId);
                sb.append(" ");
                sb.append("  AND l2.parent_id = ");
                sb.append(EndPointId);
            }
        }
        return sb.toString();
    }
}