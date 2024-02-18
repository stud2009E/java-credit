package ru.sber.edu.controller.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.entity.CreditOffer;
import ru.sber.edu.entity.CreditOfferStatus;
import ru.sber.edu.entity.FavoriteCredit;
import ru.sber.edu.entity.auth.User;
import ru.sber.edu.exception.CreditBankException;
import ru.sber.edu.exception.CreditBaseException;
import ru.sber.edu.projection.CreditOffersDTO;
import ru.sber.edu.service.CreditService;
import ru.sber.edu.service.UserService;
import ru.sber.edu.ui.DisplayMode;
import ru.sber.edu.ui.table.TableUtil;

import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private UserService userService;

    @Autowired
    private CreditService creditService;

    @GetMapping(value = "/credit/{creditId}")
    public String credit(@PathVariable(name = "creditId") Long creditId, Model model) {

        try {
            Credit credit = creditService.findById(creditId);

            User user = userService.getUser();
            List<FavoriteCredit> favorites = creditService.findFavoriteCredit(user, credit);

            model.addAttribute("isFavorite", !favorites.isEmpty());
            model.addAttribute("credit", credit);
            model.addAttribute("mode", DisplayMode.DISPLAY.toString());

        }catch (CreditBankException e){
            model.addAttribute("errorMessage", "This loan does not exist");
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

            Credit savedCredit = creditService.findById(credit.getCreditId());
            User user = userService.getUser();
            List<FavoriteCredit> favorites = creditService.findFavoriteCredit(user, savedCredit);

            model.addAttribute("isFavorite", !favorites.isEmpty());
            model.addAttribute("credit", savedCredit);
            model.addAttribute("mode", DisplayMode.DISPLAY.toString());

            return "/client/creditShow";
        }

        return "redirect:/";
    }

    @PostMapping(value = "/favorites/add")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String addToFavorites(Credit credit) {
        User user = userService.getUser();

        try{
            Credit savedCredit = creditService.findById(credit.getCreditId());
            List<FavoriteCredit> favorites = creditService.findFavoriteCredit(user, savedCredit);
            if (favorites.isEmpty()) {
                creditService.addFavoriteCredit(new FavoriteCredit(user, savedCredit));
            }
        }catch (CreditBaseException ignored){

        }

        return "redirect:/credit/" + credit.getCreditId();
    }

    @PostMapping(value = "/favorites/remove")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String removeFromFavorites(@RequestHeader("Referer") String referer, Credit credit, Model model) {

        try {
            Credit savedCredit = creditService.findById(credit.getCreditId());
            User user = userService.getUser();
            List<FavoriteCredit> favorites = creditService.findFavoriteCredit(user, savedCredit);
            if (!favorites.isEmpty()) {
                creditService.removeFavoriteCredit(new FavoriteCredit(user, savedCredit));
            }
        }catch (CreditBaseException ignored){}

        if (referer.contains("favorites")){
            return "redirect:/favorites";
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
    public String favorites(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
                            @RequestParam(value = "size", required = false, defaultValue = "10") int pageSize,
                            Model model) {
        User user = userService.getUser();

        Pageable pageable = PageRequest.of(--pageNumber, pageSize);
        Page<FavoriteCredit> credits = creditService.findFavoriteCreditByUser(user, pageable);

        TableUtil<FavoriteCredit> util = new TableUtil<>(credits, FavoriteCredit.getColumns());
        util.addTableDataToModel(model, FavoriteCredit::getCredit);

        return "/client/favorites";
    }


    @GetMapping(value = "/my/requests")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String myRequests(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
                             @RequestParam(value = "size", required = false, defaultValue = "5") int pageSize,
                             Model model) {

        User user = userService.getUser();

        Pageable pageable = PageRequest.of(--pageNumber, pageSize);
        Page<CreditOffer> creditOffers = creditService.findCreditOfferByUser(user, pageable);

        TableUtil<CreditOffer> util = new TableUtil<>(creditOffers, CreditOffer.getClientColumns());
        util.addTableDataToModel(model, creditOffer -> {
            Credit credit = creditOffer.getCredit();
            CreditOfferStatus.StatusType statusType = creditOffer.getCreditOfferStatus().getStatusName();

            return new CreditOffersDTO(credit.getCreditId(), credit.getName(), 0L, "", "", statusType);
        });
        util.addPagingDataToModel(model);
        model.addAttribute("action", "/my/requests");

        return "/client/myRequests";
    }

    @GetMapping(value = "/my/credits")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String myCredits(Model model) {

        return "/client/myCredits";
    }

}