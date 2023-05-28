package org.geekhub.kovalchuk.controler;

import org.apache.logging.log4j.util.Strings;
import org.geekhub.kovalchuk.model.dto.TripSearchResultDto;
import org.geekhub.kovalchuk.model.entity.User;
import org.geekhub.kovalchuk.model.request.SearchParamsRequest;
import org.geekhub.kovalchuk.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    CityInOperationService cityInOperationService;
    FlightMatcherService flightMatcherService;
    UserService userService;
    RoleService roleService;
    FavouriteService favouriteService;


    public MainController(CityInOperationService cityInOperationService,
                          FlightMatcherService flightMatcherService,
                          UserService userService,
                          RoleService roleService,
                          FavouriteService favouriteService) {
        this.cityInOperationService = cityInOperationService;
        this.flightMatcherService = flightMatcherService;
        this.userService = userService;
        this.roleService = roleService;
        this.favouriteService = favouriteService;
    }

    @GetMapping("/")
    public String showIndexPage(Authentication auth, Model model) {
        model.addAttribute("cashedCitiesInOperation", cityInOperationService.getCitiesForView());
        model.addAttribute("role", roleService.getRole(auth));
        return "about";
    }

    @GetMapping("/flight")
    public String showFlightsPage(Authentication auth,
                                  Model model,
                                  @RequestParam(required = false, defaultValue = "0") long favouriteId) {
        model.addAttribute("cashedLocationsInOperation", cityInOperationService.getLocationsForView());
        model.addAttribute("role", roleService.getRole(auth));
        model.addAttribute("parameters", favouriteService.getSearchParameters(favouriteId));
        return "flight";
    }

    @GetMapping("/admin")
    public String showAdminPage(Authentication auth, Model model) {
        model.addAttribute("cashedLocationsInOperation", cityInOperationService.getLocationsForView());
        model.addAttribute("role", roleService.getRole(auth));
        return "admin";
    }

    @PostMapping("/search")
    @ResponseBody
    public TripSearchResultDto getLocationsList(@AuthenticationPrincipal UserDetails userDetails,
                                                @ModelAttribute SearchParamsRequest searchParamsRequest) {
        String username;
        if (userDetails == null) {
            username = Strings.EMPTY;
        } else {
            username = userDetails.getUsername();
        }
        return flightMatcherService.getMatchedFlights(username, searchParamsRequest);
    }

    @GetMapping("/user-info")
    @ResponseBody
    public String getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return Strings.EMPTY;
        } else {
            return userDetails.getUsername();
        }
    }

    @GetMapping("/registration")
    public String showRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(User user) {
        User userFromDb = userService.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return "registration";
        }

        userService.addNewUser(user);
        return "redirect:/login";
    }

    @GetMapping("/exist")
    @ResponseBody
    public boolean isUserExists(@RequestParam String username, @RequestParam(required = false) String password) {
        if (password == null) {
            User userFromDb = userService.findByUsername(username);
            return userFromDb != null;
        } else {
            return userService.isUserFoundByUsernameAndPassword(username, password);
        }
    }

    @GetMapping("/blocked")
    @ResponseBody
    public boolean isUserBlocked(@RequestParam String username) {
        return userService.isUserBlockedByUsername(username);
    }
}