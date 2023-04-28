package org.geekhub.kovalchuk.controler;

import org.geekhub.kovalchuk.model.ManyFlightsUnit;
import org.geekhub.kovalchuk.model.request.SearchParamsRequest;
import org.geekhub.kovalchuk.service.CityInOperationService;
import org.geekhub.kovalchuk.service.FlightMatcherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    CityInOperationService cityInOperationService;
    FlightMatcherService flightMatcherService;

    public MainController(CityInOperationService cityInOperationService, FlightMatcherService flightMatcherService) {
        this.cityInOperationService = cityInOperationService;
        this.flightMatcherService = flightMatcherService;
    }

    @GetMapping("/")
    public String showPage(Model model) {
        model.addAttribute("cashedLocationsInOperation", cityInOperationService.getLocationsForView());
        return "flight";
    }

    @PostMapping("/search")
    @ResponseBody
    public List<ManyFlightsUnit> getLocationsList(@ModelAttribute SearchParamsRequest searchParamsRequest) {
        return flightMatcherService.getFlightsByRequestParams(searchParamsRequest);
    }
}
