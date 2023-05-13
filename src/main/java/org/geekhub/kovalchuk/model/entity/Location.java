package org.geekhub.kovalchuk.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
public class Location {
    @Id
    private Long entityId;
    private Long parentId;
    private String name;
    @OneToOne
    @JoinColumn(name = "type_id")
    private LocationType locationType;
    private String iata;
    private double latitude;
    private double longitude;
    private LocalDateTime addDate;
    private String skyScannerAbbreviation;
    private boolean activity;

    public Location() {
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate;
    }

    public String getSkyScannerAbbreviation() {
        return skyScannerAbbreviation;
    }

    public void setSkyScannerAbbreviation(String skyScannerAbbreviation) {
        this.skyScannerAbbreviation = skyScannerAbbreviation;
    }

    public boolean isActivity() {
        return activity;
    }

    public void setActivity(boolean activity) {
        this.activity = activity;
    }
}
