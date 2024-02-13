package ru.sber.edu.controller.bank;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.sber.edu.entity.Bank;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.service.CreditService;
import ru.sber.edu.service.UserService;
import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/bank")
public class BankController {

    private final CreditService creditService;

    @Autowired
    private UserService userService;

    //TODO remove
    private final Bank bank;

    public BankController(CreditService creditService) {
        this.creditService = creditService;

        //Long bankId = userService.getAuthority();
        Long bankId = 1L;
        bank = new Bank();
    }

    @GetMapping(value = "credit/all")
    public String credits(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                      @RequestParam(value = "size", defaultValue = "10") int pageSize,
                      @RequestParam(defaultValue = "creditId") String sortBy,
                      @RequestParam(defaultValue = "acs") String order,
                      Model model){

        //Long bankId = userService.getAuthority();
        //TODO remove
        Long bankId = 1L;
        Page<Credit> credits = creditService.findByBankId(bankId, pageNumber, pageSize, sortBy, order);

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("order", order);
        model.addAttribute("credits", credits);

        return "credits";
    }

    @GetMapping(path = {"credit/search"})
    public String search(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                         @RequestParam(value = "size", defaultValue = "10") int pageSize,
                         @RequestParam(defaultValue = "creditId") String sortBy,
                         @RequestParam(defaultValue = "asc") String order,
                         @RequestParam() String name,
                         Model model){

        //Long bankId = userService.getAuthority();
        Long bankId = 1L;

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
    public String showCredit(@PathVariable("creditId") Long creditId, Model model){

        Optional<Credit> creditOptional = creditService.findById(creditId);

        creditOptional.ifPresent(credit -> model.addAttribute("credit", credit));
        model.addAttribute("action", "/bank/credit/edit");

        return "creditShow";
    }


    @PostMapping(value = "/credit/edit")
    public String editCredit(Credit credit, Model model){
        model.addAttribute("credit", credit);

        return "redirect:/bank/credit/edit/"+credit.getCreditId();
    }


    @GetMapping(value = "/credit/edit/{creditId}")
    public String creditEdit(@PathVariable("creditId") Long creditId, Model model){
        Optional<Credit> creditOptional = creditService.findById(creditId);

        creditOptional.ifPresent(credit -> model.addAttribute("credit", credit));
        model.addAttribute("mode", "edit");

        return "creditEdit";
    }


    @PostMapping(value = "/credit/edit/{creditId}")
    public String saveCredit(@Valid Credit credit, Errors errors, Model model){
        if (errors.hasErrors()){
            model.addAttribute("mode", "edit");
            return "creditEdit";
        }

        //Long bankId = userService.getAuthority();
        //TODO
        Long bankId = 1L;
        credit = creditService.saveCredit(credit);

        return "redirect:/bank/credit/" + credit.getCreditId();
    }


    @GetMapping(value = "/credit/create")
    public String creditCreate(Model model){
        Credit credit = new Credit();

        //TODO
        credit.setBankId(1L);
        credit.setDateFrom(LocalDate.now());
        credit.setDateTo(LocalDate.now());

        model.addAttribute("credit", credit);
        return "creditCreate";
    }


    @PostMapping(value = "/credit/create")
    public String saveCreate(@Valid Credit credit, Errors errors, Model model){

        if (errors.hasErrors()){
            return "creditCreate";
        }

        //TODO
        //Long bankId = userService.getAuthority();
        Long bankId = 1L;

        Credit newCredit = creditService.createCredit(credit, bankId);

        return "redirect:/bank/credit/"+newCredit.getCreditId();
    }

    @GetMapping(value = "/profile")
    public String profile(Model model){

        model.addAttribute("bank", bank);
        return "bankProfile";
    }

    @GetMapping(value = "client/all")
    public String clients(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                      @RequestParam(value = "size", defaultValue = "10") int pageSize,
                      @RequestParam(defaultValue = "creditId") String sortBy,
                      @RequestParam(defaultValue = "acs") String order,
                      Model model){

        //Long bankId = userService.getAuthority();
        Long bankId = 1L;
        Page<Credit> credits = creditService.findByBankId(bankId, pageNumber, pageSize, sortBy, order);

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("order", order);
        model.addAttribute("credits", credits);

        return "credits";
    }

    @RequestMapping(path = {"client/all"})
    public String clients(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                         @RequestParam(value = "size", defaultValue = "10") int pageSize,
                         @RequestParam(defaultValue = "creditId") String sortBy,
                         @RequestParam(defaultValue = "asc") String order,
                         @RequestParam() String name,
                         Model model){

        //Long bankId = userService.getAuthority();
        Long bankId = 1L;

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

}