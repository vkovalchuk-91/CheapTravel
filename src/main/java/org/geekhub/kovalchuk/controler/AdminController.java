package org.geekhub.kovalchuk.controler;

import org.geekhub.kovalchuk.model.dto.AbbreviationsSelectorDto;
import org.geekhub.kovalchuk.model.dto.CitiesSelectorDto;
import org.geekhub.kovalchuk.model.dto.UserAccessSelectorDto;
import org.geekhub.kovalchuk.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {
    CityInOperationService cityInOperationService;
    UserService userService;
    MonthPricesService monthPricesService;
    RouteService routeService;
    LocationsService locationsService;

    public AdminController(CityInOperationService cityInOperationService,
                           UserService userService,
                           MonthPricesService monthPricesService,
                           RouteService routeService,
                           LocationsService locationsService) {
        this.cityInOperationService = cityInOperationService;
        this.userService = userService;
        this.monthPricesService = monthPricesService;
        this.routeService = routeService;
        this.locationsService = locationsService;
    }

    @GetMapping("/cities")
    public CitiesSelectorDto getCities() {
        return cityInOperationService.getEuropeanCitiesForView();
    }

    @PostMapping("/cities")
    public void updateCities(@RequestBody Map<Long, Boolean> updatedCities) {
        cityInOperationService.updateCitiesInOperation(updatedCities);
    }

    @GetMapping("/abbreviations")
    public AbbreviationsSelectorDto getAbbreviations() {
        return locationsService.getEuropeanCitiesAbbreviationsForView();
    }

    @PostMapping("/abbreviations")
    public void updateAbbreviations(@RequestBody Map<Long, String> updatedCities) {
        locationsService.updateEuropeanCitiesAbbreviations(updatedCities);
    }

    @GetMapping("/users")
    public List<UserAccessSelectorDto> getUsers() {
        return userService.getUsersForView();
    }

    @PostMapping("/users")
    public void updateUsers(@RequestBody Map<String, Boolean> updatedUsers) {
        userService.updateUsers(updatedUsers);
    }

    @GetMapping("/months")
    public int getMaxMonthsCashingNumber() {
        return monthPricesService.getMaxMonthsCashingNumber();
    }

    @PostMapping("/months")
    public void updateMaxMonthsCashingNumber(@RequestBody int updatedMaxMonthsCashingNumber) {
        monthPricesService.setMaxMonthsCashingNumber(updatedMaxMonthsCashingNumber);
    }

    @PostMapping("/routes")
    public void updateRoutes() {
        routeService.setNeedToStartUpdateRoutes();
    }

    @PostMapping("/locations")
    public void updateLocations() {
        locationsService.saveOrUpdateLocationsToDb();
    }
}