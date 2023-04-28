package org.geekhub.kovalchuk.model.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Flight {
    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    @JoinColumn(name = "route_id")
    private Route route;
    private LocalDate flightDate;
    @OneToOne
    @JoinColumn(name = "currency_code")
    private Currency currency;
    private double price;
    private LocalDateTime addDate;
    private boolean activity;

    public Flight() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(LocalDate flightDate) {
        this.flightDate = flightDate;
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

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ",\nflightDate " + flightDate +
                ",\nroute " + route.getFromCity().getName() + "-" + route.getToCity().getName() +
                ",\nprice " + price + " " + currency.getCode() +
                ",\naddDate " + addDate +
                ", activity " + activity +
                "}\n\n";
    }
}
