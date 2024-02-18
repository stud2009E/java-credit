package ru.sber.edu.controller.bank;

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
import ru.sber.edu.entity.Bank;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.entity.CreditOffer;
import ru.sber.edu.entity.CreditOfferStatus;
import ru.sber.edu.exception.CreditBankException;
import ru.sber.edu.projection.ClientOfBankDTO;
import ru.sber.edu.projection.CreditOffersDTO;
import ru.sber.edu.service.BankService;
import ru.sber.edu.service.CreditOfferService;
import ru.sber.edu.service.CreditService;
import ru.sber.edu.ui.DisplayMode;
import ru.sber.edu.ui.table.TableUtil;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bank")
@PreAuthorize("hasAuthority('BANK')")
public class BankController {

    @Autowired
    private CreditService creditService;

    @Autowired
    private BankService bankService;

    @Autowired
    private CreditOfferService creditOfferService;

    /**
     * Отображение списка кредитов Банка
     * @param pageNumber
     * @param pageSize
     * @param model
     * @return
     */
    @GetMapping(value = "/credit/all")
    public String showCredits(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                          @RequestParam(value = "size", defaultValue = "10") int pageSize,
                          Model model) {

        Pageable pageable = PageRequest.of(--pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "creditId"));

        TableUtil<Credit> util = new TableUtil<>(creditService.findByMyBank(pageable), Credit.getBankColumns());
        util.addTableDataToModel(model);
        util.addPagingDataToModel(model);

        model.addAttribute("pageAction","/bank/credit/all");
        model.addAttribute("searchAction","/bank/credit/search");

        return "bank/credits";
    }

    /**
     * Поиск кредита по имени
     * @param pageNumber
     * @param pageSize
     * @param value
     * @param model
     * @return
     */
    @GetMapping(path = {"/credit/search"})
    public String searchCredit(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                               @RequestParam(value = "size", defaultValue = "10") int pageSize,
                               @RequestParam(value = "value", required = false) String value,
                         Model model) {

        model.addAttribute("pageAction","/bank/credit/all");
        model.addAttribute("searchAction","/bank/credit/search");

        if (!value.isEmpty()) {

            Pageable pageable = PageRequest.of(--pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "creditId"));

            TableUtil<Credit> util = new TableUtil<>(creditService.findByNameAndMyBank(value, pageable), Credit.getBankColumns());
            util.addTableDataToModel(model);
            util.addPagingDataToModel(model);

            model.addAttribute("searchValue", value);

            return "bank/credits";
        } else {

            return "redirect:/bank/credit/all";
        }
    }

    /**
     * Просмотр кредита
     * @param creditId
     * @param model
     * @return
     */
    @GetMapping(value = "/credit/{creditId}")
    public String showCredit(@PathVariable("creditId") Long creditId, Model model) {

        try {
            Credit credit = creditService.findByCreditIdAndBankId(creditId, bankService.getMyBank().getBankId());
            model.addAttribute("credit", credit);
            model.addAttribute("mode", DisplayMode.DISPLAY.toString());
            model.addAttribute("action", "/bank/credit/edit");

        }catch (CreditBankException e){
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "bank/creditShow";

    }

    /**
     * Переход на изменение кредита
     * @param credit
     * @param model
     * @return
     */
    @PostMapping(value = "/credit/edit")
    public String editCredit(Credit credit, Model model) {

        model.addAttribute("credit", credit);

        return "redirect:/bank/credit/edit/" + credit.getCreditId();
    }

    /**
     * Изменение кредита
     * @param creditId
     * @param model
     * @return
     */
    @GetMapping(value = "/credit/edit/{creditId}")
    public String creditEdit(@PathVariable("creditId") Long creditId, Model model) {

        try {
            Credit credit = creditService.findByCreditIdAndBankId(creditId, bankService.getMyBank().getBankId());
            model.addAttribute("credit", credit);
            model.addAttribute("mode", DisplayMode.EDIT.toString());
        }catch (CreditBankException e){
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "bank/creditEdit";
    }

    /**
     * Сохранение изменений кредита
     * @param credit
     * @param errors
     * @param model
     * @return
     */
    @PostMapping(value = "/credit/edit/{creditId}")
    public String saveCredit(@Valid Credit credit, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("mode", DisplayMode.EDIT.toString());
            return "bank/creditEdit";
        }

        credit.setBankId(bankService.getMyBank().getBankId());
        credit = creditService.saveCredit(credit);

        return "redirect:/bank/credit/" + credit.getCreditId();
    }

    /**
     * Создание нового кредита
     * @param model
     * @return
     */
    @GetMapping(value = "/credit/create")
    public String createCredit(Model model) {
        Credit credit = new Credit();
        Bank bank = bankService.getMyBank();

        credit.setBankId(bank.getBankId());
        credit.setDateFrom(LocalDate.now());
        credit.setDateTo(LocalDate.now());

        model.addAttribute("mode", DisplayMode.CREATE.toString());
        model.addAttribute("credit", credit);

        return "bank/creditCreate";
    }

    /**
     * Сохранение создания кредита
     * @param credit
     * @param errors
     * @return
     */
    @PostMapping(value = "/credit/create")
    public String saveCreate(@Valid Credit credit, Errors errors) {

        if (errors.hasErrors()) {
            return "bank/creditCreate";
        }

        Credit newCredit = creditService.createCredit(credit);

        return "redirect:/bank/credit/" + newCredit.getCreditId();
    }

    /**
     * Профиль банка
     * @param model
     * @return
     */
    @GetMapping(value = "/profile")
    public String bankProfile(Model model) {
        Bank bank = bankService.getMyBank();

        model.addAttribute("bank", bank);
        return "bank/bankProfile";
    }

    /**
     * Вывод списка клиентов банка(пользователи взявшие кредит)
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param order
     * @param model
     * @return
     */
    @GetMapping(value = "client/all")
    public String bankClients(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                          @RequestParam(value = "size", defaultValue = "10") int pageSize,
                          @RequestParam(defaultValue = "user.userId") String sortBy,
                          @RequestParam(defaultValue = "acs") String order,
                          Model model) {

        Page<ClientOfBankDTO> clients = creditOfferService.findClientsOfBank(bankService.getMyBank(), pageNumber, pageSize, sortBy, order);

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("order", order);
        model.addAttribute("clients", clients);

        return "bank/clients";
    }

    /**
     * Вывод списка заявок на кредит
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param order
     * @param model
     * @return
     */
    @GetMapping(value = "/creditOffer/all")
    public String creditOffer(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                              @RequestParam(value = "size", defaultValue = "10") int pageSize,
                              @RequestParam(defaultValue = "credit.creditId") String sortBy,
                              @RequestParam(defaultValue = "acs") String order,
                              Model model) {

        Pageable pageable = PageRequest.of(--pageNumber, pageSize );

        Bank bank = bankService.getMyBank();
        Page<CreditOffersDTO> credits = creditOfferService.findAllByBank(bank, pageable);

        TableUtil<CreditOffersDTO> util = new TableUtil<>(credits, CreditOffersDTO.getColumns());
        util.addTableDataToModel(model);

        return "bank/creditOffers";

    }

    /**
     * Просмотр заявки на кредит
     * @param creditId
     * @param userId
     * @param model
     * @return
     */
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

    /**
     * Ободрение заявки на кредит
     * @param creditId
     * @param userId
     * @param model
     * @return
     */
    @GetMapping(value = "/creditOffer/credit/{creditId}/user/{userId}/approve")
    public String approveCreditOffer(@PathVariable("creditId") Long creditId, @PathVariable("userId") Long userId,
                              Model model) {
        Optional<CreditOffer> creditOfferOptional = creditOfferService.findById(creditId, userId);

        CreditOffer creditOffer = creditOfferOptional.get();
        creditOffer.setCreditOfferStatus(new CreditOfferStatus(CreditOfferStatus.StatusType.APPROVE));

        creditOfferService.save(creditOffer);

        return "redirect:/bank/creditOffer/all";
    }

    /**
     * Отклонение заявки на кредит
     * @param creditId
     * @param userId
     * @param model
     * @return
     */
    @GetMapping(value = "/creditOffer/credit/{creditId}/user/{userId}/reject")
    public String rejectCreditOffer(@PathVariable("creditId") Long creditId, @PathVariable("userId") Long userId,
                          Model model) {
        Optional<CreditOffer> creditOfferOptional = creditOfferService.findById(creditId, userId);

        CreditOffer creditOffer = creditOfferOptional.get();
        creditOffer.setCreditOfferStatus(new CreditOfferStatus(CreditOfferStatus.StatusType.REJECT));

        creditOfferService.save(creditOffer);

        return "redirect:/bank/creditOffer/all";
    }
}