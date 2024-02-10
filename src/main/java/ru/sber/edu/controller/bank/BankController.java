package ru.sber.edu.controller.bank;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.sber.edu.entity.Bank;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.service.CreditService;
import ru.sber.edu.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/bank")
public class BankController {

    private final CreditService creditService;

    @Autowired
    private UserService userService;

    private Bank bank;

    public BankController(CreditService creditService) {
        this.creditService = creditService;
    }

    @GetMapping(value = "credit/all")
    public String all(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                      @RequestParam(value = "size", defaultValue = "10") int pageSize,
                      @RequestParam(defaultValue = "creditId") String sortBy,
                      @RequestParam(defaultValue = "acs") String order,
                      Model model){

        Long bankId = userService.getAuthority();

        Page<Credit> credits = creditService.findByBankId(bankId, pageNumber, pageSize, sortBy, order);

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("order", order);
        model.addAttribute("credits", credits);

        return "credits";
    }

    @RequestMapping(path = {"credit/search"})
    public String search(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                         @RequestParam(value = "size", defaultValue = "10") int pageSize,
                         @RequestParam(defaultValue = "creditId") String sortBy,
                         @RequestParam(defaultValue = "asc") String order,
                         @RequestParam() String name,
                         Model model){

        Long bankId = userService.getAuthority();

        if(!name.isEmpty()){
            Page<Credit> credits = creditService.findByNameAndBankId(name, bankId, pageNumber, pageSize, sortBy, order);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("sortBy", sortBy);
            model.addAttribute("order", order);
            model.addAttribute("credits", credits);
            return "credits";
        }else{
            return "redirect:/bank/credit/all";
        }
    }

    @GetMapping(value = "credit/{creditId}")
    public String showCredit(@PathVariable("creditId") Long creditId,
                             Model model){

        Optional<Credit> credit = creditService.findById(creditId);

        if (credit.isEmpty()){
            return null;
        }

        model.addAttribute("credit", credit.get());
        model.addAttribute("disable", true);

        return "credit";
    }

    @PostMapping(value = "credit/{creditId}")
    public String editCredit(@PathVariable("creditId") Long creditId,
                             Model model){

        Optional<Credit> credit = creditService.findById(creditId);

        if (credit.isEmpty()){
            return null;
        }

        model.addAttribute("credit", credit.get());

        return "credit";
    }

    @GetMapping(value = "/credit/create")
    public String creditCreate(Model model){
        Credit credit = new Credit();

        credit.setDateFrom(LocalDateTime.now());
        credit.setDateTo(LocalDateTime.now());

        model.addAttribute("credit", credit);
        return "creditCreate";
    }

    @PostMapping(value = "/credit/create")
    public String saveCreate(@Valid Credit credit,
                             Model model){

        Long bankId = userService.getAuthority();
        bankId = 1L;

        Credit newCredit = creditService.createCredit(credit, bankId);

        return "redirect:/bank/credit/"+newCredit.getCreditId();
    }

}