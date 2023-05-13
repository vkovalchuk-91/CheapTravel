package org.geekhub.kovalchuk.reports.report1;

public class Report1Data {
    String cityName;
    int availableRoutesToCity;
    int availableRoutesFromCity;
    double percentageCitiesWithRoutesToCity;
    double percentageCitiesWithRoutesFromCity;
    double averageTicketPriceToCity;
    double averageTicketPriceFromCity;

    public Report1Data() {
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getAvailableRoutesToCity() {
        return availableRoutesToCity;
    }

    public void setAvailableRoutesToCity(int availableRoutesToCity) {
        this.availableRoutesToCity = availableRoutesToCity;
    }

    public int getAvailableRoutesFromCity() {
        return availableRoutesFromCity;
    }

    public void setAvailableRoutesFromCity(int availableRoutesFromCity) {
        this.availableRoutesFromCity = availableRoutesFromCity;
    }

    public double getPercentageCitiesWithRoutesToCity() {
        return percentageCitiesWithRoutesToCity;
    }

    public void setPercentageCitiesWithRoutesToCity(double percentageCitiesWithRoutesToCity) {
        this.percentageCitiesWithRoutesToCity = percentageCitiesWithRoutesToCity;
    }

    public double getPercentageCitiesWithRoutesFromCity() {
        return percentageCitiesWithRoutesFromCity;
    }

    public void setPercentageCitiesWithRoutesFromCity(double percentageCitiesWithRoutesFromCity) {
        this.percentageCitiesWithRoutesFromCity = percentageCitiesWithRoutesFromCity;
    }

    public double getAverageTicketPriceToCity() {
        return averageTicketPriceToCity;
    }

    public void setAverageTicketPriceToCity(double averageTicketPriceToCity) {
        this.averageTicketPriceToCity = averageTicketPriceToCity;
    }

    public double getAverageTicketPriceFromCity() {
        return averageTicketPriceFromCity;
    }

    public void setAverageTicketPriceFromCity(double averageTicketPriceFromCity) {
        this.averageTicketPriceFromCity = averageTicketPriceFromCity;
    }
}
