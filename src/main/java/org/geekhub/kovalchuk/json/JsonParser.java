package org.geekhub.kovalchuk.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.geekhub.kovalchuk.json.entity.LocationJsonEntity;
import org.geekhub.kovalchuk.json.entity.MonthPriceJsonEntity;

public class JsonParser {
    public static LocationJsonEntity getLocations(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, LocationJsonEntity.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static MonthPriceJsonEntity getMonthPrices(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, MonthPriceJsonEntity.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
