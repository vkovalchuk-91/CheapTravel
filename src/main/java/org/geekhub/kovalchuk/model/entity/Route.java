package org.geekhub.kovalchuk.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "from_city_id")
    private Location fromCity;
    @OneToOne
    @JoinColumn(name = "to_city_id")
    private Location toCity;
    private boolean haveFlights;
    private LocalDateTime checkDate;
    private boolean activity;

    public Route() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Location getFromCity() {
        return fromCity;
    }

    public void setFromCity(Location fromCity) {
        this.fromCity = fromCity;
    }

    public Location getToCity() {
        return toCity;
    }

    public void setToCity(Location toCity) {
        this.toCity = toCity;
    }

    public boolean isHaveFlights() {
        return haveFlights;
    }

    public void setHaveFlights(boolean haveFlights) {
        this.haveFlights = haveFlights;
    }

    public LocalDateTime getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(LocalDateTime checkDate) {
        this.checkDate = checkDate;
    }

    public boolean isActivity() {
        return activity;
    }

    public void setActivity(boolean activity) {
        this.activity = activity;
    }
}
