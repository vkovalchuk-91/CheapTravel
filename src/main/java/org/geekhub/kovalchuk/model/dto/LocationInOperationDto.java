package org.geekhub.kovalchuk.model.dto;

public class LocationInOperationDto {
    private final long id;
    private final boolean isCity;
    private final String name;

    public LocationInOperationDto(long id, boolean isCity, String name) {
        this.id = id;
        this.isCity = isCity;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public boolean isCity() {
        return isCity;
    }

    public String getName() {
        return name;
    }
}
