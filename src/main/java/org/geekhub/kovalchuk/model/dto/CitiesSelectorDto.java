package org.geekhub.kovalchuk.model.dto;

import java.util.List;
import java.util.Map;

public class CitiesSelectorDto {
    Map<String, List<City>> europeCitiesWithCountry;

    public CitiesSelectorDto(Map<String, List<City>> europeCitiesWithCountry) {
        this.europeCitiesWithCountry = europeCitiesWithCountry;
    }

    public Map<String, List<City>> getEuropeCitiesWithCountry() {
        return europeCitiesWithCountry;
    }

    public void setEuropeCitiesWithCountry(Map<String, List<City>> europeCitiesWithCountry) {
        this.europeCitiesWithCountry = europeCitiesWithCountry;
    }

    public static class City {
        long cityId;
        String cityName;
        boolean isInOperation;

        public City(long cityId, String cityName, boolean isInOperation) {
            this.cityId = cityId;
            this.cityName = cityName;
            this.isInOperation = isInOperation;
        }

        public long getCityId() {
            return cityId;
        }

        public void setCityId(long cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public boolean isInOperation() {
            return isInOperation;
        }

        public void setInOperation(boolean inOperation) {
            isInOperation = inOperation;
        }
    }
}
