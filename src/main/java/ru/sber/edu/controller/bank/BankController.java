package ru.sber.edu.controller.bank;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.sber.edu.entity.Bank;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.entity.CreditOffer;
import ru.sber.edu.entity.CreditOfferStatus;
import ru.sber.edu.projection.ClientOfBank;
import ru.sber.edu.projection.CreditOffersDTO;
import ru.sber.edu.service.BankService;
import ru.sber.edu.service.CreditOfferService;
import ru.sber.edu.service.CreditService;
import ru.sber.edu.ui.DisplayMode;
import ru.sber.edu.ui.table.TableUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private CreditService creditService;

    @Autowired
    private BankService bankService;

    @Autowired
    private CreditOfferService creditOfferService;

    @GetMapping(value = "/credit/all")
    public String credits(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                          @RequestParam(value = "size", defaultValue = "10") int pageSize,
                          @RequestParam(defaultValue = "creditId") String sortBy,
                          @RequestParam(defaultValue = "acs") String order,
                          Model model) {

        Bank bank = bankService.getMyBank();
        Page<Credit> credits = creditService.findByBank(bank, pageNumber, pageSize, sortBy, order);

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
                         Model model) {

        if (!name.isEmpty()) {
            Bank bank = bankService.getMyBank();
            Page<Credit> credits = creditService.findByNameAndBankId(name, bank, pageNumber, pageSize, sortBy, order);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("sortBy", sortBy);
            model.addAttribute("order", order);
            model.addAttribute("credits", credits);

            return "credits";
        } else {
            return "redirect:/bank/credit/all";
        }
    }

    @GetMapping(value = "/credit/{creditId}")
    public String showCredit(@PathVariable("creditId") Long creditId, Model model) {

        Optional<Credit> creditOptional = creditService.findById(creditId);

        creditOptional.ifPresent(credit -> model.addAttribute("credit", credit));
        model.addAttribute("action", "/bank/credit/edit");

        return "creditShow";
    }

    @PostMapping(value = "/credit/edit")
    public String editCredit(Credit credit, Model model) {
        model.addAttribute("credit", credit);

        return "redirect:/bank/credit/edit/" + credit.getCreditId();
    }

    @GetMapping(value = "/credit/edit/{creditId}")
    public String creditEdit(@PathVariable("creditId") Long creditId, Model model) {
        //Optional<Credit> creditOptional = creditService.findById(creditId);
        Optional<Credit> creditOptional = creditService.findByIdAndBank(creditId, bankService.getMyBank());

        creditOptional.ifPresent(credit -> model.addAttribute("credit", credit));
        model.addAttribute("mode", "edit");

        return "creditEdit";
    }

    @PostMapping(value = "/credit/edit/{creditId}")
    public String saveCredit(@Valid Credit credit, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("mode", "edit");
            return "creditEdit";
        }

        credit.setBank(bankService.getMyBank());
        credit = creditService.saveCredit(credit);

        return "redirect:/bank/credit/" + credit.getCreditId();
    }

    @GetMapping(value = "/credit/create")
    public String creditCreate(Model model) {
        Credit credit = new Credit();
        Bank bank = bankService.getMyBank();

        credit.setBank(bank);
        credit.setDateFrom(LocalDate.now());
        credit.setDateTo(LocalDate.now());

        model.addAttribute("credit", credit);

        return "creditCreate";
    }


    @PostMapping(value = "/credit/create")
    public String saveCreate(@Valid Credit credit, Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "creditCreate";
        }

        Bank bank = bankService.getMyBank();
        Credit newCredit = creditService.createCredit(credit, bank.getBankId());

        return "redirect:/bank/credit/" + newCredit.getCreditId();
    }

    @GetMapping(value = "/profile")
    public String profile(Model model) {
        Bank bank = bankService.getMyBank();

        model.addAttribute("bank", bank);
        return "bankProfile";
    }

    @GetMapping(value = "client/all")
    public String clients(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                          @RequestParam(value = "size", defaultValue = "10") int pageSize,
                          @RequestParam(defaultValue = "user_id") String sortBy,
                          @RequestParam(defaultValue = "acs") String order,
                          Model model) {
        Bank bank = bankService.getMyBank();
        Page<ClientOfBank> clients = bankService.findClientsByBankId(bank.getBankId(), pageNumber, pageSize, sortBy, order);

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("order", order);
        model.addAttribute("clients", clients);

        return "clients";
    }

    @GetMapping(value = "/creditOffers")
    public String creditOffer(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                              @RequestParam(value = "size", defaultValue = "10") int pageSize,
                              @RequestParam(defaultValue = "credit.creditId") String sortBy,
                              @RequestParam(defaultValue = "acs") String order,
                              Model model) {

        Sort sorting = Sort.by(sortBy);
        sorting = order.equals("acs") ? sorting.ascending() : sorting.descending();
        Pageable pageable = PageRequest.of(--pageNumber, pageSize, sorting );

        Bank bank = bankService.getMyBank();
        Page<CreditOffersDTO> credits = creditOfferService.findAllByBank(bank, pageable);

        TableUtil util = new TableUtil(CreditOffersDTO.getColumns(), credits);
        util.fill(model);

        return "bank/creditOffers";

    }

    @GetMapping(value = "/creditOffer/credit/{creditId}/user/{userId}")
    public String creditOffer(@PathVariable("creditId") Long creditId,
                              @PathVariable("userId") Long userId,
                              Model model) {

        Optional<CreditOffer> creditOfferOptional = creditOfferService.findById(creditId, userId);

        List<CreditOfferStatus.StatusType> statusList = Arrays.stream(CreditOfferStatus.StatusType.values()).toList();

        model.addAttribute("statusList",statusList);

        creditOfferOptional.ifPresent(creditOffer -> model.addAttribute("creditOffer", creditOffer));

        String mode = DisplayMode.DISPLAY.toString();

        if (creditOfferOptional.get().getCreditOfferStatus().statusName == CreditOfferStatus.StatusType.REQUEST){
            mode = DisplayMode.EDIT.toString();
        }

        model.addAttribute("mode", mode);

        return "bank/creditOffer";
    }

    @GetMapping(value = "/creditOffer/credit/{creditId}/user/{userId}/approve")
    public String approve(@PathVariable("creditId") Long creditId, @PathVariable("userId") Long userId,
                              Model model) {

        Optional<CreditOffer> creditOfferOptional = creditOfferService.findById(creditId, userId);

        CreditOffer creditOffer = creditOfferOptional.get();

        creditOffer.setCreditOfferStatus(new CreditOfferStatus(CreditOfferStatus.StatusType.APPROVE));

        creditOfferService.save(creditOffer);

        return "redirect:/bank/creditOffer/credit/" + creditOffer.getCredit().getCreditId() +
                "/user/" + creditOffer.getUser().getUserId();
    }

    @GetMapping(value = "/creditOffer/credit/{creditId}/user/{userId}/reject")
    public String reject(@PathVariable("creditId") Long creditId, @PathVariable("userId") Long userId,
                          Model model) {

        Optional<CreditOffer> creditOfferOptional = creditOfferService.findById(creditId, userId);

        CreditOffer creditOffer = creditOfferOptional.get();

        creditOffer.setCreditOfferStatus(new CreditOfferStatus(CreditOfferStatus.StatusType.REJECT));

        creditOfferService.save(creditOffer);

        return "redirect:/bank/creditOffer/credit/" + creditOffer.getCredit().getCreditId() +
                "/user/" + creditOffer.getUser().getUserId();
    }
}