package org.geekhub.kovalchuk.model.dto;

import java.util.List;
import java.util.Map;

public class AbbreviationsSelectorDto {
    Map<String, List<City>> europeCitiesWithCountry;

    public AbbreviationsSelectorDto(Map<String, List<City>> europeCitiesWithCountry) {
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
        String abbreviation;

        public City(long cityId, String cityName, String abbreviation) {
            this.cityId = cityId;
            this.cityName = cityName;
            this.abbreviation = abbreviation;
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

        public String getAbbreviation() {
            return abbreviation;
        }

        public void setAbbreviation(String abbreviation) {
            this.abbreviation = abbreviation;
        }
    }
}
