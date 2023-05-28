package org.geekhub.kovalchuk.model.dto;

import java.time.LocalDate;

public class SearchParametersDto {
    private int showResults;
    private int flightNumber;
    private long startPointsId1 = 0;
    private long endPointsId1 = 0;
    private long startPointsId2 = 0;
    private long endPointsId2 = 0;
    private long startPointsId3 = 0;
    private long endPointsId3 = 0;
    private long startPointsId4 = 0;
    private long endPointsId4 = 0;
    private LocalDate startTripDate;
    private LocalDate endTripDate;
    private int daysInPointMin;
    private int daysInPointMax;
    private int totalDaysMin;
    private int totalDaysMax;
    private int totalCostMin;
    private int totalCostMax;

    public SearchParametersDto() {
    }

    public int getShowResults() {
        return showResults;
    }

    public void setShowResults(int showResults) {
        this.showResults = showResults;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public long getStartPointsId1() {
        return startPointsId1;
    }

    public void setStartPointsId1(long startPointsId1) {
        this.startPointsId1 = startPointsId1;
    }

    public long getEndPointsId1() {
        return endPointsId1;
    }

    public void setEndPointsId1(long endPointsId1) {
        this.endPointsId1 = endPointsId1;
    }

    public long getStartPointsId2() {
        return startPointsId2;
    }

    public void setStartPointsId2(long startPointsId2) {
        this.startPointsId2 = startPointsId2;
    }

    public long getEndPointsId2() {
        return endPointsId2;
    }

    public void setEndPointsId2(long endPointsId2) {
        this.endPointsId2 = endPointsId2;
    }

    public long getStartPointsId3() {
        return startPointsId3;
    }

    public void setStartPointsId3(long startPointsId3) {
        this.startPointsId3 = startPointsId3;
    }

    public long getEndPointsId3() {
        return endPointsId3;
    }

    public void setEndPointsId3(long endPointsId3) {
        this.endPointsId3 = endPointsId3;
    }

    public long getStartPointsId4() {
        return startPointsId4;
    }

    public void setStartPointsId4(long startPointsId4) {
        this.startPointsId4 = startPointsId4;
    }

    public long getEndPointsId4() {
        return endPointsId4;
    }

    public void setEndPointsId4(long endPointsId4) {
        this.endPointsId4 = endPointsId4;
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
