package org.geekhub.kovalchuk.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.geekhub.kovalchuk.json.entity.LocationJsonResponse;
import org.geekhub.kovalchuk.json.entity.MonthPriceJsonResponse;

public class JsonParser {
    public static LocationJsonResponse getLocations(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, LocationJsonResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static MonthPriceJsonResponse getMonthPrices(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, MonthPriceJsonResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
