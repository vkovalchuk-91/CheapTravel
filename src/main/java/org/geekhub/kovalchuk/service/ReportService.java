package org.geekhub.kovalchuk.service;

import org.geekhub.kovalchuk.reports.report1.Report1Data;
import org.geekhub.kovalchuk.reports.report1.Report1Utils;
import org.geekhub.kovalchuk.reports.report2.Report2Data;
import org.geekhub.kovalchuk.reports.report2.Report2Utils;
import org.geekhub.kovalchuk.reports.report3.Report3Data;
import org.geekhub.kovalchuk.reports.report3.Report3Utils;
import org.geekhub.kovalchuk.repository.ReportsRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    private final ReportsRepository reportsRepository;

    public ReportService(ReportsRepository reportsRepository) {
        this.reportsRepository = reportsRepository;
    }

    public void exportReport1(HttpServletResponse response) throws IOException {
        Map<String, Integer> availableRoutesToCity = reportsRepository.getAvailableRoutesToCity();
        Map<String, Integer> availableRoutesFromCity = reportsRepository.getAvailableRoutesFromCity();
        Map<String, Double> averageTicketPriceToCity = reportsRepository.getAverageTicketPriceToCity();
        Map<String, Double> averageTicketPriceFromCity = reportsRepository.getAverageTicketPriceFromCity();

        List<Report1Data> reportData = new ArrayList<>();
        HashSet<String> cities = reportsRepository.getCities();
        int totalCitiesNumber = cities.size();
        for (String city : cities) {
            Report1Data rowData = new Report1Data();
            rowData.setCityName(city);
            if (availableRoutesToCity.containsKey(city)) {
                Integer availableRoutes = availableRoutesToCity.get(city);

                rowData.setAvailableRoutesToCity(availableRoutes);
                rowData.setPercentageCitiesWithRoutesToCity(availableRoutes / (double) (totalCitiesNumber - 1));
            }
            if (availableRoutesFromCity.containsKey(city)) {
                Integer availableRoutes = availableRoutesFromCity.get(city);

                rowData.setAvailableRoutesFromCity(availableRoutes);
                rowData.setPercentageCitiesWithRoutesFromCity(availableRoutes / (double) (totalCitiesNumber - 1));
            }
            if (averageTicketPriceToCity.containsKey(city)) {
                rowData.setAverageTicketPriceToCity(averageTicketPriceToCity.get(city));
            }
            if (averageTicketPriceFromCity.containsKey(city)) {
                rowData.setAverageTicketPriceFromCity(averageTicketPriceFromCity.get(city));
            }
            reportData.add(rowData);
        }

        Report1Utils report1Utils = new Report1Utils(reportData);
        report1Utils.exportReport1(response);
    }

    public void exportReport2(HttpServletResponse response, long cityId) throws IOException {
        Map<String, List<Double>> ticketPriceStatisticByFromCity =
                reportsRepository.getTicketPriceStatisticByFromCity(cityId);
        List<Report2Data> reportData = new ArrayList<>();
        ticketPriceStatisticByFromCity.forEach((key, value) -> {
            Report2Data rowData = new Report2Data();
            rowData.setRouteName(key);
            rowData.setMinTicketPrice(value.get(0));
            rowData.setAverageTicketPrice(value.get(1));
            rowData.setMaxTicketPrice(value.get(2));
            reportData.add(rowData);
        });

        Report2Utils report2Utils = new Report2Utils(reportData);
        report2Utils.exportReport2(response);
    }

    public void exportReport3(HttpServletResponse response) throws IOException {
        Map<String, Double> averageTicketPriceByRoute = reportsRepository.getAverageTicketPriceByRoute();
        List<Report3Data> reportData = new ArrayList<>();
        averageTicketPriceByRoute.forEach((key, value) -> {
            Report3Data rowData = new Report3Data();
            rowData.setRouteName(key);
            rowData.setAverageTicketPrice(value);
            reportData.add(rowData);
        });

        Report3Utils report3Utils = new Report3Utils(reportData);
        report3Utils.exportReport3(response);
    }
}
