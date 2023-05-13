package org.geekhub.kovalchuk.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

public interface ReportsRepository {
    HashSet<String> getCities();
    Map<String, Integer> getAvailableRoutesToCity();
    Map<String, Integer> getAvailableRoutesFromCity();
    Map<String, Double> getAverageTicketPriceToCity();
    Map<String, Double> getAverageTicketPriceFromCity();
    Map<String, List<Double>> getTicketPriceStatisticByFromCity(long cityId);
    Map<String, Double> getAverageTicketPriceByRoute();
}
