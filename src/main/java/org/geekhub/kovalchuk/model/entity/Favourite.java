package org.geekhub.kovalchuk.model.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "favourite")
public class Favourite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int flightNumber;
    @OneToOne
    @JoinColumn(name = "start_point_id1")
    private Location startPoint1;
    @OneToOne
    @JoinColumn(name = "end_point_id1")
    private Location endPoint1;
    @OneToOne
    @JoinColumn(name = "start_point_id2")
    private Location startPoint2;
    @OneToOne
    @JoinColumn(name = "end_point_id2")
    private Location endPoint2;
    @OneToOne
    @JoinColumn(name = "start_point_id3")
    private Location startPoint3;
    @OneToOne
    @JoinColumn(name = "end_point_id3")
    private Location endPoint3;
    @OneToOne
    @JoinColumn(name = "start_point_id4")
    private Location startPoint4;
    @OneToOne
    @JoinColumn(name = "end_point_id4")
    private Location endPoint4;
    private LocalDate startTripDate;
    private LocalDate endTripDate;
    private int daysInPointMin;
    private int daysInPointMax;
    private int totalDaysMin;
    private int totalDaysMax;
    private int totalCostMin;
    private int totalCostMax;

    public Favourite() {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Location getStartPoint1() {
        return startPoint1;
    }

    public void setStartPoint1(Location startPoint1) {
        this.startPoint1 = startPoint1;
    }

    public Location getEndPoint1() {
        return endPoint1;
    }

    public void setEndPoint1(Location endPoint1) {
        this.endPoint1 = endPoint1;
    }

    public Location getStartPoint2() {
        return startPoint2;
    }

    public void setStartPoint2(Location startPoint2) {
        this.startPoint2 = startPoint2;
    }

    public Location getEndPoint2() {
        return endPoint2;
    }

    public void setEndPoint2(Location endPoint2) {
        this.endPoint2 = endPoint2;
    }

    public Location getStartPoint3() {
        return startPoint3;
    }

    public void setStartPoint3(Location startPoint3) {
        this.startPoint3 = startPoint3;
    }

    public Location getEndPoint3() {
        return endPoint3;
    }

    public void setEndPoint3(Location endPoint3) {
        this.endPoint3 = endPoint3;
    }

    public Location getStartPoint4() {
        return startPoint4;
    }

    public void setStartPoint4(Location startPoint4) {
        this.startPoint4 = startPoint4;
    }

    public Location getEndPoint4() {
        return endPoint4;
    }

    public void setEndPoint4(Location endPoint4) {
        this.endPoint4 = endPoint4;
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