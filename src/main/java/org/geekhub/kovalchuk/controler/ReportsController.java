package org.geekhub.kovalchuk.controler;

import org.geekhub.kovalchuk.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ReportsController {
    private final ReportService reportService;

    public ReportsController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/report1")
    public void exportReport1(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Cities_Information.xlsx";
        response.setHeader(headerKey, headerValue);
        reportService.exportReport1(response);
    }

    @GetMapping("/report2")
    public void exportReport2(HttpServletResponse response, @RequestParam long cityId) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=From_city_routes_statistic.xlsx";
        response.setHeader(headerKey, headerValue);
        reportService.exportReport2(response, cityId);
    }

    @GetMapping("/report3")
    public void exportReport3(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Top_100_cheapest_routes.xlsx";
        response.setHeader(headerKey, headerValue);
        reportService.exportReport3(response);
    }
}
