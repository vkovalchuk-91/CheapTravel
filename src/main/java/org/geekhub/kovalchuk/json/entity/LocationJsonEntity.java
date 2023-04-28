package org.geekhub.kovalchuk.json.entity;

import java.util.Map;

public class LocationJsonEntity {
    public enum Type {
        PLACE_TYPE_AIRPORT, PLACE_TYPE_CITY, PLACE_TYPE_CONTINENT, PLACE_TYPE_COUNTRY, PLACE_TYPE_ISLAND
    }

    private String status;
    private Map<String, Place> places;

    public String getStatus() {
        return status;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    public Map<String, Place> getPlaces() {
        return places;
    }

    public void setPlaces(Map<String, Place> value) {
        this.places = value;
    }

    @Override
    public String toString() {
        return "Geolocation{" + "status='" + status + '\'' + ", places=" + places + '}';
    }

    public static class Place {
        private long entityId;
        private long parentId;
        private String name;
        private Type type;
        private String iata;
        private Coordinates coordinates;

        public long getEntityId() {
            return entityId;
        }

        public void setEntityId(long value) {
            this.entityId = value;
        }

        public long getParentId() {
            return parentId;
        }

        public void setParentId(long value) {
            this.parentId = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String value) {
            this.name = value;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type value) {
            this.type = value;
        }

        public String getIata() {
            return iata;
        }

        public void setIata(String value) {
            this.iata = value;
        }

        public Coordinates getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(Coordinates value) {
            this.coordinates = value;
        }

        @Override
        public String toString() {
            return "\nPlace{" + "entityId='" + entityId + '\'' + ", parentId='" + parentId + '\'' + ", name='" + name + '\'' + ", type=" + type + ", iata='" + iata + '\'' + ", coordinates=" + coordinates + "}";
        }

        public static class Coordinates {
            private double latitude;
            private double longitude;

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double value) {
                this.latitude = value;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double value) {
                this.longitude = value;
            }

            @Override
            public String toString() {
                return "\nCoordinates{" + "latitude=" + latitude + ", longitude=" + longitude + "}";
            }
        }
    }
}