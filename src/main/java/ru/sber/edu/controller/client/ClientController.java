package ru.sber.edu.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.exception.CreditBaseException;
import ru.sber.edu.service.CreditService;
import ru.sber.edu.service.UserService;

import java.util.Optional;

@Controller
@RequestMapping(value = "/credit")
public class ClientController {

    @Autowired
    private UserService userService;

    @Autowired
    private CreditService creditService;

    @GetMapping(value = "/{creditId}")
    public String credit(@PathVariable(name = "creditId") Long creditId, Model model){

        Optional<Credit> optional = creditService.findById(creditId);

        if (optional.isEmpty()){
            model.addAttribute("errorMessage", "This loan does not exist");
        }else {
            model.addAttribute("credit", optional.get());
        }

        return "/client/creditShow";
    }

    @PostMapping(value = "/request")
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
}