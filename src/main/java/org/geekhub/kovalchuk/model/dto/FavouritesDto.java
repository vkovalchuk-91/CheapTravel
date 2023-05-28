package org.geekhub.kovalchuk.model.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FavouritesDto {
    private Map<List<RouteCities>, List<SearchLinkParameters>> favouritesMap;

    public FavouritesDto(Map<List<RouteCities>, List<SearchLinkParameters>> favouritesRawData) {
        favouritesMap = favouritesRawData;
    }

    public Map<List<RouteCities>, List<SearchLinkParameters>> getFavouritesMap() {
        return favouritesMap;
    }

    public void setFavouritesMap(Map<List<RouteCities>, List<SearchLinkParameters>> favouritesMap) {
        this.favouritesMap = favouritesMap;
    }

    public static class RouteCities {

        private String fromCity;
        private String toCity;

        public RouteCities(String fromCity, String toCity) {
            this.fromCity = fromCity;
            this.toCity = toCity;
        }

        public String getFromCity() {
            return fromCity;
        }

        public void setFromCity(String fromCity) {
            this.fromCity = fromCity;
        }

        public String getToCity() {
            return toCity;
        }

        public void setToCity(String toCity) {
            this.toCity = toCity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof RouteCities)) return false;
            RouteCities that = (RouteCities) o;
            return fromCity.equals(that.fromCity) && toCity.equals(that.toCity);
        }

        @Override
        public int hashCode() {
            return Objects.hash(fromCity, toCity);
        }
    }
    public static class SearchLinkParameters {

        private long favouriteId;
        private LocalDate startTripDate;
        private LocalDate endTripDate;
        private int daysInPointMin;
        private int daysInPointMax;
        private int totalDaysMin;
        private int totalDaysMax;
        private int totalCostMin;
        private int totalCostMax;

        public SearchLinkParameters(long favouriteId,
                                    LocalDate startTripDate,
                                    LocalDate endTripDate,
                                    int daysInPointMin,
                                    int daysInPointMax,
                                    int totalDaysMin,
                                    int totalDaysMax,
                                    int totalCostMin,
                                    int totalCostMax) {
            this.favouriteId = favouriteId;
            this.startTripDate = startTripDate;
            this.endTripDate = endTripDate;
            this.daysInPointMin = daysInPointMin;
            this.daysInPointMax = daysInPointMax;
            this.totalDaysMin = totalDaysMin;
            this.totalDaysMax = totalDaysMax;
            this.totalCostMin = totalCostMin;
            this.totalCostMax = totalCostMax;
        }

        public long getFavouriteId() {
            return favouriteId;
        }

        public void setFavouriteId(long favouriteId) {
            this.favouriteId = favouriteId;
        }

        public LocalDate getStartTripDate() {
            return startTripDate;
        }

        public void setStartTripDate(LocalDate startTripDate) {
            this.startTripDate = startTripDate;
        }

        public LocalDate getEndTripDate() {
            return endTripDate;
        }

        public void setEndTripDate(LocalDate endTripDate) {
            this.endTripDate = endTripDate;
        }

        public int getDaysInPointMin() {
            return daysInPointMin;
        }

        public void setDaysInPointMin(int daysInPointMin) {
            this.daysInPointMin = daysInPointMin;
        }

        public int getDaysInPointMax() {
            return daysInPointMax;
        }

        public void setDaysInPointMax(int daysInPointMax) {
            this.daysInPointMax = daysInPointMax;
        }

        public int getTotalDaysMin() {
            return totalDaysMin;
        }

        public void setTotalDaysMin(int totalDaysMin) {
            this.totalDaysMin = totalDaysMin;
        }

        public int getTotalDaysMax() {
            return totalDaysMax;
        }

        public void setTotalDaysMax(int totalDaysMax) {
            this.totalDaysMax = totalDaysMax;
        }

        public int getTotalCostMin() {
            return totalCostMin;
        }

        public void setTotalCostMin(int totalCostMin) {
            this.totalCostMin = totalCostMin;
        }

        public int getTotalCostMax() {
            return totalCostMax;
        }

        public void setTotalCostMax(int totalCostMax) {
            this.totalCostMax = totalCostMax;
        }
    }
}
