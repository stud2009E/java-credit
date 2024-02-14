package ru.sber.edu.controller.client;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.entity.FavoriteCredit;
import ru.sber.edu.entity.auth.User;
import ru.sber.edu.exception.CreditBaseException;
import ru.sber.edu.service.CreditService;
import ru.sber.edu.service.UserService;
import ru.sber.edu.ui.DisplayMode;

import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {

    @Autowired
    private UserService userService;

    @Autowired
    private CreditService creditService;

    @GetMapping(value = "/credit/{creditId}")
    public String credit(@PathVariable(name = "creditId") Long creditId, Model model) {

        Optional<Credit> optionalCredit = creditService.findById(creditId);

        if (optionalCredit.isEmpty()) {
            model.addAttribute("errorMessage", "This loan does not exist");
        } else {
            User user = userService.getUser();
            List<FavoriteCredit> favorites = creditService.findFavoriteCredit(user, optionalCredit.get());

            model.addAttribute("isFavorite", !favorites.isEmpty());
            model.addAttribute("credit", optionalCredit.get());
        }

        return "/client/creditShow";
    }


    @PostMapping(value = "/credit/request")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String creditRequest(Credit credit, Model model) {
        try {
            creditService.createCreditOffer(credit);
        } catch (CreditBaseException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/client/creditShow";
        }

        return "redirect:/";
    }

    @PostMapping(value = "/favorites/add")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String addToFavorites(Credit credit, Model model) {
        Optional<Credit> optionalCredit = creditService.findById(credit.getCreditId());
        User user = userService.getUser();

        if (optionalCredit.isPresent()) {
            List<FavoriteCredit> favorites = creditService.findFavoriteCredit(user, optionalCredit.get());
            if (favorites.isEmpty()) {
                creditService.addFavoriteCredit(new FavoriteCredit(user, optionalCredit.get()));
            }
        }

        return "redirect:/credit/" + credit.getCreditId();
    }

    @PostMapping(value = "/favorites/remove")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String removeFromFavorites(Credit credit, Model model) {
        Optional<Credit> optionalCredit = creditService.findById(credit.getCreditId());
        User user = userService.getUser();

        if (optionalCredit.isPresent()) {
            List<FavoriteCredit> favorites = creditService.findFavoriteCredit(user, optionalCredit.get());
            if (!favorites.isEmpty()) {
                creditService.removeFavoriteCredit(new FavoriteCredit(user, optionalCredit.get()));
            }
        }

        return "redirect:/credit/" + credit.getCreditId();
    }


    @GetMapping(value = "/profile")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String profile(Model model) {
        User user = userService.getUser();
        model.addAttribute("user", user);
        model.addAttribute("mode", DisplayMode.DISPLAY.toString());
        model.addAttribute("isLogged", userService.isLogged());

        return "/client/profile";
    }

    @GetMapping(value = "/profile/edit")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String profileEdit(Model model) {
        User user = userService.getUser();

        model.addAttribute("user", user);
        model.addAttribute("mode", DisplayMode.EDIT.toString());
        model.addAttribute("isLogged", userService.isLogged());

        return "/client/profile";
    }

    @PostMapping(value = "/profile/edit")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String profileSave(@Valid User user, Errors errors, Model model) {
        User currentUser = userService.getUser();

        currentUser.setPhone(user.getPhone());
        currentUser.setEmail(user.getEmail());

        if (errors.hasFieldErrors("phone") || errors.hasFieldErrors("email")) {
            user.setUsername(currentUser.getUsername());
            user.setFirstName(currentUser.getFirstName());
            user.setLastName(currentUser.getLastName());

            model.addAttribute("mode", DisplayMode.EDIT.toString());
            model.addAttribute("isLogged", userService.isLogged());

            return "/client/profile";
        }

        userService.update(currentUser);

        return "redirect:/profile";
    }


    @GetMapping(value = "/favorites")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String favorites(Model model) {

        return "/client/favorites";
    }


    @GetMapping(value = "/my/requests")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String myRequests(Model model) {

        return "/client/myRequests";
    }

    @GetMapping(value = "/my/credits")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String myCredits(Model model) {

        return "/client/myCredits";
    }

}