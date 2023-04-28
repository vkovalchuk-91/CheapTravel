package org.geekhub.kovalchuk.model;

import org.geekhub.kovalchuk.model.entity.Flight;

public class QueryReturnFlightUnit {
    private final Flight departFlight;
    private final Flight returnFlight;
    private final double totalCost;

    public QueryReturnFlightUnit(Flight departFlight, Flight returnFlight) {
        this.departFlight = departFlight;
        this.returnFlight = returnFlight;
        totalCost = departFlight.getPrice() + returnFlight.getPrice();
    }

    public double getTotalCost() {
        return totalCost;
    }

    @Override
    public String toString() {
        return "QueryReturnFlightUnit:\n" +
                "Depart Flight: " + departFlight.getFlightDate() + ' ' +
                departFlight.getRoute().getFromCity().getName() + "-" + departFlight.getRoute().getToCity().getName() +
                ' ' + departFlight.getPrice() + ' ' + departFlight.getCurrency().getCode() + '\n' +
                "Return Flight: " + returnFlight.getFlightDate() + ' ' +
                returnFlight.getRoute().getFromCity().getName() + "-" + returnFlight.getRoute().getToCity().getName() +
                ' ' + returnFlight.getPrice() + ' ' + returnFlight.getCurrency().getCode() + '\n' +
                "Total price: " + totalCost + ' ' + departFlight.getCurrency().getCode() + '\n' +
                '\n';
    }
}