package org.geekhub.kovalchuk.controler;

import org.apache.logging.log4j.util.Strings;
import org.geekhub.kovalchuk.model.request.SearchParamsRequest;
import org.geekhub.kovalchuk.service.FavouriteService;
import org.geekhub.kovalchuk.service.RoleService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FavouritesController {
    private final FavouriteService favouriteService;
    private final RoleService roleService;

    public FavouritesController(FavouriteService favouriteService, RoleService roleService) {
        this.favouriteService = favouriteService;
        this.roleService = roleService;
    }

    @GetMapping("/favourites")
    public String showFavouritesPage(@AuthenticationPrincipal UserDetails userDetails,
                                     Authentication auth,
                                     Model model) {
        String username;
        if (userDetails == null) {
            username = Strings.EMPTY;
        } else {
            username = userDetails.getUsername();
        }
        model.addAttribute("favourites", favouriteService.getFavourites(username));
        model.addAttribute("role", roleService.getRole(auth));
        return "favourites";
    }

    @PostMapping("/favourites")
    @ResponseBody
    public long addToFavourite(@AuthenticationPrincipal UserDetails userDetails,
                               @ModelAttribute SearchParamsRequest searchParamsRequest) {
        String username;
        if (userDetails == null) {
            username = Strings.EMPTY;
        } else {
            username = userDetails.getUsername();
        }
        return favouriteService.addFavourite(username, searchParamsRequest);
    }

    @DeleteMapping("/favourites")
    @ResponseBody
    public void deleteFromFavourite(@RequestBody long favouriteId) {
        favouriteService.deleteFavourite(favouriteId);
    }
}
