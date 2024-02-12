package ru.sber.edu.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.edu.entity.Credit;
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
    public String creditRequest(Credit credit){




        return "redirect:/";
    }
}