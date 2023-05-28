package org.geekhub.kovalchuk.model.dto;

import java.time.LocalDate;
import java.util.List;

public class TripSearchResultDto {
    private final List<Trip> trips;
    private final long favouriteId;

    public TripSearchResultDto(List<Trip> trips, long favouriteId) {
        this.trips = trips;
        this.favouriteId = favouriteId;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public long getFavouriteId() {
        return favouriteId;
    }

    public static class Trip {
        private List<FlightInfo> flights;
        private double totalCost;
        private String link;

        public Trip(List<FlightInfo> flights, double totalCost, List<String[]> abbreviations) {
            this.flights = flights;
            this.totalCost = totalCost;
            link = getFlightSkyScannerLink(flights, abbreviations);
        }

        public List<FlightInfo> getFlights() {
            return flights;
        }

        public void setFlights(List<FlightInfo> flights) {
            this.flights = flights;
        }

        public double getTotalCost() {
            return totalCost;
        }

        public void setTotalCost(double totalCost) {
            this.totalCost = totalCost;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        private String getFlightSkyScannerLink(List<FlightInfo> flights, List<String[]> abbreviations) {
            StringBuilder sb = new StringBuilder();
            sb.append("https://www.skyscanner.com.ua/transport/d/");
            for (int i = 0; i < flights.size(); i++) {
                String[] routeAbbr = abbreviations.get(i);
                FlightInfo flightInfo = flights.get(i);
                sb.append(routeAbbr[0]).append("/");
                sb.append(flightInfo.getDate()).append("/");
                sb.append(routeAbbr[1]).append("/");
            }
            sb.append("?stops=!oneStop,!twoPlusStops");
            return sb.toString();
        }

        public static class FlightInfo {
            private String fromCity;
            private String toCity;
            private LocalDate date;

            public FlightInfo(String fromCity, String toCity, LocalDate date) {
                this.fromCity = fromCity;
                this.toCity = toCity;
                this.date = date;
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

            public LocalDate getDate() {
                return date;
            }

            public void setDate(LocalDate date) {
                this.date = date;
            }
        }
    }
}
