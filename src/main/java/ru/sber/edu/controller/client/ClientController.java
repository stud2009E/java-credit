package ru.sber.edu.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.exception.CreditBaseException;
import ru.sber.edu.service.CreditService;
import ru.sber.edu.service.UserService;

import java.util.Optional;

@Controller
public class ClientController {

    @Autowired
    private UserService userService;

    @Autowired
    private CreditService creditService;

    @GetMapping(value = "/credit/{creditId}")
    public String credit(@PathVariable(name = "creditId") Long creditId, Model model){

        Optional<Credit> optional = creditService.findById(creditId);

        if (optional.isEmpty()){
            model.addAttribute("errorMessage", "This loan does not exist");
        }else {
            model.addAttribute("credit", optional.get());
        }

        return "/client/creditShow";
    }


    @PostMapping(value = "/credit/request")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String creditRequest(Credit credit, Model model){
        try{
            creditService.createCreditOffer(credit);
        }catch (CreditBaseException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/client/creditShow";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/profile")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String profile(Model model){

        return "/client/profile";
    }


    @GetMapping(value = "/favorites")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String favorites(Model model){

        return "/client/favorites";
    }


    @GetMapping(value = "/my/requests")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String myRequests(Model model){

        return "/client/myRequests";
    }

    @GetMapping(value = "/my/credits")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String myCredits(Model model){

        return "/client/myCredits";
    }

}