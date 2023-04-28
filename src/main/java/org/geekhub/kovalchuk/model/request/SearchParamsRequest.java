package org.geekhub.kovalchuk.model.request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SearchParamsRequest {
    boolean lowPriceSwitcher;
    int flightNumber;
    List<Long> startPointsIds;
    List<Long> endPointsIds;
    LocalDate startTripDate;
    LocalDate endTripDate;
    int daysInPointMin;
    int daysInPointMax;
    int totalDaysMin;
    int totalDaysMax;
    int totalCostMin;
    int totalCostMax;

    public SearchParamsRequest(int flightNumber, boolean lowPriceSwitcher, String startPoint1, String endPoint1,
                               String startPoint2, String endPoint2, String startPoint3, String endPoint3,
                               String startPoint4, String endPoint4, String tripDates, String daysInPoint,
                               String totalDays, String totalCost) {
        this.flightNumber = flightNumber;
        this.lowPriceSwitcher = lowPriceSwitcher;

        startPointsIds = new ArrayList<>();
        endPointsIds = new ArrayList<>();
        if (flightNumber >= 1) {
            startPointsIds.add(Long.parseLong(startPoint1.replaceAll(" ", "")));
            endPointsIds.add(Long.parseLong(endPoint1.replaceAll(" ", "")));
        }
        if (flightNumber >= 2) {
            startPointsIds.add(Long.parseLong(startPoint2.replaceAll(" ", "")));
            endPointsIds.add(Long.parseLong(endPoint2.replaceAll(" ", "")));
        }
        if (flightNumber >= 3) {
            startPointsIds.add(Long.parseLong(startPoint3.replaceAll(" ", "")));
            endPointsIds.add(Long.parseLong(endPoint3.replaceAll(" ", "")));
        }
        if (flightNumber == 4) {
            startPointsIds.add(Long.parseLong(startPoint4.replaceAll(" ", "")));
            endPointsIds.add(Long.parseLong(endPoint4.replaceAll(" ", "")));
        }

        startTripDate = LocalDate.parse(tripDates.split(" - ")[0]);
        endTripDate = LocalDate.parse(tripDates.split(" - ")[1]);
        daysInPointMin = Integer.parseInt(daysInPoint.split(",")[0]);
        daysInPointMax = Integer.parseInt(daysInPoint.split(",")[1]);
        totalDaysMin = Integer.parseInt(totalDays.split(",")[0]);
        totalDaysMax = Integer.parseInt(totalDays.split(",")[1]);
        totalCostMin = Integer.parseInt(totalCost.split(",")[0]);
        totalCostMax = Integer.parseInt(totalCost.split(",")[1]);
    }

    public boolean isLowPriceSwitcher() {
        return lowPriceSwitcher;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public List<Long> getStartPointsIds() {
        return startPointsIds;
    }

    public List<Long> getEndPointsIds() {
        return endPointsIds;
    }

    public LocalDate getStartTripDate() {
        return startTripDate;
    }

    public LocalDate getEndTripDate() {
        return endTripDate;
    }

    public int getDaysInPointMin() {
        return daysInPointMin;
    }

    public int getDaysInPointMax() {
        return daysInPointMax;
    }

    public int getTotalDaysMin() {
        return totalDaysMin;
    }

    public int getTotalDaysMax() {
        return totalDaysMax;
    }

    public int getTotalCostMin() {
        return totalCostMin;
    }

    public int getTotalCostMax() {
        return totalCostMax;
    }

    @Override
    public String toString() {
        return "SearchParamsRequest{" +
                "flightNumber=" + flightNumber +
                ", startPointsIds=" + startPointsIds +
                ", endPointsIds=" + endPointsIds +
                ", startTripDate=" + startTripDate +
                ", endTripDate=" + endTripDate +
                ", daysInPointMin=" + daysInPointMin +
                ", daysInPointMax=" + daysInPointMax +
                ", totalDaysMin=" + totalDaysMin +
                ", totalDaysMax=" + totalDaysMax +
                ", totalCostMin=" + totalCostMin +
                ", totalCostMax=" + totalCostMax +
                '}';
    }
}
