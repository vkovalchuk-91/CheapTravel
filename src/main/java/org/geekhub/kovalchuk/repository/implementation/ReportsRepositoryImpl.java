package org.geekhub.kovalchuk.repository.implementation;

import org.geekhub.kovalchuk.config.ApplicationPropertiesConfig;
import org.geekhub.kovalchuk.repository.ReportsRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ReportsRepositoryImpl implements ReportsRepository {
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final ApplicationPropertiesConfig properties;

    public ReportsRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                 ApplicationPropertiesConfig properties) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.properties = properties;
    }

    @Override
    public HashSet<String> getCities() {
        String sql = "SELECT l.name " +
                "FROM city_in_operation " +
                "         JOIN location l on l.entity_id = city_in_operation.city_id " +
                "WHERE city_in_operation.activity = true";
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        return namedParameterJdbcTemplate.query(sql, parameters, rs -> {
            HashSet<String> set = new HashSet<>();
            while (rs.next()) {
                String value = rs.getString("name");
                set.add(value);
            }
            return set;
        });
    }

    @Override
    public Map<String, Integer> getAvailableRoutesToCity() {
        String sql = "SELECT l.name, count(r.from_city_id) " +
                "FROM city_in_operation " +
                "         JOIN route r on city_in_operation.city_id = r.to_city_id " +
                "         JOIN location l on l.entity_id = city_in_operation.city_id " +
                "WHERE have_flights = true " +
                "  AND r.activity = true " +
                "GROUP BY l.name";
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        return namedParameterJdbcTemplate.query(sql,
                parameters,
                rs -> {
                    Map<String, Integer> map = new HashMap<>();
                    while (rs.next()) {
                        String key = rs.getString("name");
                        Integer value = rs.getInt("count");
                        map.put(key, value);
                    }
                    return map;
                });
    }

    @Override
    public Map<String, Integer> getAvailableRoutesFromCity() {
        String sql = "SELECT l.name, count(r.from_city_id) " +
                "FROM city_in_operation " +
                "         JOIN route r on city_in_operation.city_id = r.from_city_id " +
                "         JOIN location l on l.entity_id = city_in_operation.city_id " +
                "WHERE have_flights = true " +
                "  AND r.activity = true " +
                "GROUP BY l.name";
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        return namedParameterJdbcTemplate.query(sql,
                parameters,
                rs -> {
                    Map<String, Integer> map = new HashMap<>();
                    while (rs.next()) {
                        String key = rs.getString("name");
                        Integer value = rs.getInt("count");
                        map.put(key, value);
                    }
                    return map;
                });
    }

    @Override
    public Map<String, Double> getAverageTicketPriceToCity() {
        int maxMonths = properties.getMaxMonths();
        String sql = "SELECT l.name, avg(f.price) " +
                "FROM city_in_operation " +
                "         JOIN route r on city_in_operation.city_id = r.to_city_id " +
                "         JOIN location l on l.entity_id = city_in_operation.city_id " +
                "         JOIN flight f on r.id = f.route_id " +
                "WHERE have_flights = true " +
                "  AND r.activity = true " +
                "  AND f.flight_date >= CURRENT_DATE " +
                "  AND f.flight_date <= CURRENT_DATE + INTERVAL '" + maxMonths + " months' " +
                "GROUP BY l.name";
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        return namedParameterJdbcTemplate.query(sql,
                parameters,
                rs -> {
                    Map<String, Double> map = new HashMap<>();
                    while (rs.next()) {
                        String key = rs.getString("name");
                        Double value = rs.getDouble("avg");
                        map.put(key, value);
                    }
                    return map;
                });
    }

    @Override
    public Map<String, Double> getAverageTicketPriceFromCity() {
        int maxMonths = properties.getMaxMonths();
        String sql = "SELECT l.name, avg(f.price) " +
                "FROM city_in_operation " +
                "         JOIN route r on city_in_operation.city_id = r.from_city_id " +
                "         JOIN location l on l.entity_id = city_in_operation.city_id " +
                "         JOIN flight f on r.id = f.route_id " +
                "WHERE have_flights = true " +
                "  AND r.activity = true " +
                "  AND f.flight_date >= CURRENT_DATE " +
                "  AND f.flight_date <= CURRENT_DATE + INTERVAL '" + maxMonths + " months' " +
                "GROUP BY l.name";
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        return namedParameterJdbcTemplate.query(sql,
                parameters,
                rs -> {
                    Map<String, Double> map = new HashMap<>();
                    while (rs.next()) {
                        String key = rs.getString("name");
                        Double value = rs.getDouble("avg");
                        map.put(key, value);
                    }
                    return map;
                });
    }

    @Override
    public Map<String, List<Double>> getTicketPriceStatisticByFromCity(long cityId) {
        int maxMonths = properties.getMaxMonths();
        String sql = "SELECT l.name AS from_city, l2.name AS to_city, min(f.price), avg(f.price), max(f.price) " +
                "FROM route " +
                "         JOIN location l on l.entity_id = route.from_city_id " +
                "         JOIN location l2 on l2.entity_id = route.to_city_id " +
                "         JOIN flight f on route.id = f.route_id " +
                "WHERE have_flights = true " +
                "  AND route.activity = true " +
                "  AND f.flight_date >= CURRENT_DATE " +
                "  AND f.flight_date <= CURRENT_DATE + INTERVAL '" + maxMonths + " months' " +
                "  AND route.from_city_id = :cityId " +
                "GROUP BY l.name, l2.name";
        MapSqlParameterSource parameters = new MapSqlParameterSource("cityId", cityId);

        return namedParameterJdbcTemplate.query(sql,
                parameters,
                rs -> {
                    Map<String, List<Double>> map = new HashMap<>();
                    while (rs.next()) {
                        String fromCity = rs.getString("from_city");
                        String toCity = rs.getString("to_city");
                        Double min = rs.getDouble("min");
                        Double avg = rs.getDouble("avg");
                        Double max = rs.getDouble("max");
                        List<Double> priceStatistic = new ArrayList<>();
                        priceStatistic.add(min);
                        priceStatistic.add(avg);
                        priceStatistic.add(max);
                        map.put(fromCity + " - " + toCity, priceStatistic);
                    }
                    return map;
                });
    }

    @Override
    public Map<String, Double> getAverageTicketPriceByRoute() {
        int maxMonths = properties.getMaxMonths();
        String sql = "SELECT l.name AS from_city, l2.name AS to_city, avg(f.price) " +
                "FROM route " +
                "         JOIN location l on l.entity_id = route.from_city_id " +
                "         JOIN location l2 on l2.entity_id = route.to_city_id " +
                "         JOIN flight f on route.id = f.route_id " +
                "WHERE have_flights = true " +
                "  AND route.activity = true " +
                "  AND f.flight_date >= CURRENT_DATE " +
                "  AND f.flight_date <= CURRENT_DATE + INTERVAL '" + maxMonths + " months' " +
                "GROUP BY l.name, l2.name " +
                "ORDER BY avg(f.price) " +
                "LIMIT 100";
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        return namedParameterJdbcTemplate.query(sql,
                parameters,
                rs -> {
                    Map<String, Double> map = new LinkedHashMap<>();
                    while (rs.next()) {
                        String fromCity = rs.getString("from_city");
                        String toCity = rs.getString("to_city");
                        Double value = rs.getDouble("avg");
                        map.put(fromCity + " - " + toCity, value);
                    }
                    return map;
                });
    }
}
