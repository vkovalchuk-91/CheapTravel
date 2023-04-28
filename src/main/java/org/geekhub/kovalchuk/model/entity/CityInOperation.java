package org.geekhub.kovalchuk.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CityInOperation {
    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    @JoinColumn(name = "city_id")
    private Location location;
    private LocalDateTime addDate;
    private boolean activity;
    private LocalDateTime activatingDate;

    public CityInOperation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate;
    }

    public boolean isActivity() {
        return activity;
    }

    public void setActivity(boolean activity) {
        this.activity = activity;
    }

    public LocalDateTime getActivatingDate() {
        return activatingDate;
    }

    public void setActivatingDate(LocalDateTime activatingDate) {
        this.activatingDate = activatingDate;
    }
}
