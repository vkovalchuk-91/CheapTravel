package org.geekhub.kovalchuk.controler;

import org.apache.logging.log4j.util.Strings;
import org.geekhub.kovalchuk.model.ManyFlightsUnit;
import org.geekhub.kovalchuk.model.entity.Role;
import org.geekhub.kovalchuk.model.entity.User;
import org.geekhub.kovalchuk.model.request.SearchParamsRequest;
import org.geekhub.kovalchuk.service.CityInOperationService;
import org.geekhub.kovalchuk.service.FlightMatcherService;
import org.geekhub.kovalchuk.service.RoleService;
import org.geekhub.kovalchuk.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class MainController {

    CityInOperationService cityInOperationService;
    FlightMatcherService flightMatcherService;
    UserService userService;
    RoleService roleService;

    public MainController(CityInOperationService cityInOperationService,
                          FlightMatcherService flightMatcherService,
                          UserService userService,
                          RoleService roleService) {
        this.cityInOperationService = cityInOperationService;
        this.flightMatcherService = flightMatcherService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String showIndexPage(Authentication auth, Model model) {
        model.addAttribute("role", getRole(auth));
        return "about";
    }

    @GetMapping("/flight")
    public String showFlightsPage(Authentication auth, Model model) {
        model.addAttribute("cashedLocationsInOperation", cityInOperationService.getLocationsForView());
        model.addAttribute("role", getRole(auth));
        return "flight";
    }

    @GetMapping("/admin")
    public String showAdminPage(Authentication auth, Model model) {
        model.addAttribute("cashedLocationsInOperation", cityInOperationService.getLocationsForView());
        model.addAttribute("role", getRole(auth));
        return "admin";
    }

    @PostMapping("/search")
    @ResponseBody
    public List<ManyFlightsUnit> getLocationsList(@ModelAttribute SearchParamsRequest searchParamsRequest) {
        return flightMatcherService.getFlightsByRequestParams(searchParamsRequest);
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
    public String registerUser(User user, Map<String, Object> model) {
        User userFromDb = userService.findByUsername(user.getUsername());
        if (userFromDb != null) {
            model.put("message", "true");
            return "registration";
        }

        user.setActive(true);
        Role roleUser = roleService.findByRole("ROLE_USER");
        user.setRoles(Set.of(roleUser));
        userService.save(user);

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

    private String getRole(Authentication auth) {
        if (auth == null) {
            return "NotAuthenticated";
        } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "ADMIN";
        } else {
            return "USER";
        }
    }
}