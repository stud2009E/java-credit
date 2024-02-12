package ru.sber.edu.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.service.CreditService;
import ru.sber.edu.service.UserService;
import ru.sber.edu.ui.table.TableUtil;

@Controller
@RequestMapping(value = "/credit")
public class CreditController {

    @Autowired
    private UserService userService;

    @Autowired
    private CreditService creditService;

    @GetMapping(value = "/{creditId}")
    public String credit(@PathVariable(name = "creditId") Long creditId, Model model){

        Credit credit = creditService.findById(creditId);
        model.addAttribute("credit", credit);

        return "/client/creditShow";
    }

    @PostMapping(value = "/request")
    public String creditRequest(Credit credit){
        return "redirect:/";
    }
}