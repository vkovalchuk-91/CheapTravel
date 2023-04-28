package org.geekhub.kovalchuk.model;

import org.geekhub.kovalchuk.model.entity.Flight;

import java.util.ArrayList;
import java.util.List;

public class ManyFlightsUnit {
    private final List<Flight> flights;
    private double totalCost;

    public ManyFlightsUnit(List<Flight> flights) {
        this.flights = flights;
        flights.forEach(flight -> totalCost += flight.getPrice());
    }

    public ManyFlightsUnit(Flight flight) {
        flights = new ArrayList<>();
        flights.add(flight);
        flights.forEach(f -> totalCost += f.getPrice());
    }

    public Flight getLastFlight() {
        int lastElementIndex = flights.size() - 1;
        return flights.get(lastElementIndex);
    }

    public ManyFlightsUnit getNewCopyAndAddFlight(Flight flight) {
        ManyFlightsUnit newCopy = new ManyFlightsUnit(new ArrayList<>(flights));
        newCopy.getFlights().add(flight);
        double totalCost0 = newCopy.getTotalCost();
        double totalCost1 = totalCost0 + flight.getPrice();
        newCopy.setTotalCost(totalCost1);
        return newCopy;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int flightCounter = 0;
        for (Flight flight : flights) {
            sb.append("Flight ").append(++flightCounter).append(": ");
            sb.append(flight.getFlightDate()).append(' ');
            sb.append(flight.getRoute().getFromCity().getName()).append('-');
            sb.append(flight.getRoute().getToCity().getName()).append(' ');
            sb.append(flight.getPrice()).append(' ').append(flight.getCurrency().getCode()).append('\n');
        }
        sb.append("Total price: ").append(totalCost).append(' ');
        sb.append(flights.get(0).getCurrency().getCode()).append("\n\n");

        return sb.toString();
    }
}