package org.geekhub.kovalchuk.reports.report2;

public class Report2Data {
    String routeName;
    double minTicketPrice;
    double averageTicketPrice;
    double maxTicketPrice;

    public Report2Data() {
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public double getMinTicketPrice() {
        return minTicketPrice;
    }

    public void setMinTicketPrice(double minTicketPrice) {
        this.minTicketPrice = minTicketPrice;
    }

    public double getMaxTicketPrice() {
        return maxTicketPrice;
    }

    public void setMaxTicketPrice(double maxTicketPrice) {
        this.maxTicketPrice = maxTicketPrice;
    }

    public double getAverageTicketPrice() {
        return averageTicketPrice;
    }

    public void setAverageTicketPrice(double averageTicketPrice) {
        this.averageTicketPrice = averageTicketPrice;
    }
}
