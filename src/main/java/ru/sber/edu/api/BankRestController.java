package ru.sber.edu.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.sber.edu.entity.Bank;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.entity.CreditOffer;
import ru.sber.edu.entity.CreditOfferStatus;
import ru.sber.edu.exception.CreditBaseException;
import ru.sber.edu.projection.ClientOfBankDTO;
import ru.sber.edu.projection.CreditOffersDTO;
import ru.sber.edu.service.BankService;
import ru.sber.edu.service.CreditOfferService;
import ru.sber.edu.service.CreditService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bank")
@PreAuthorize("hasAuthority('BANK')")
public class BankRestController {

    @Autowired
    private CreditService creditService;

    @Autowired
    private BankService bankService;

    @Autowired
    private CreditOfferService creditOfferService;

    /**
     * Отображение списка кредитов Банка
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/credit/all")
    public List<Credit> showCredits(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                    @RequestParam(value = "size", defaultValue = "10") int pageSize) {

        Pageable pageable = PageRequest.of(--pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "creditId"));
        return creditService.findByMyBank(pageable).stream().toList();
    }

    /**
     * Поиск кредита по имени
     *
     * @param pageNumber
     * @param pageSize
     * @param value
     * @return
     */
    @GetMapping(path = {"/credit/search"})
    public List<Credit> searchCredit(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                     @RequestParam(value = "size", defaultValue = "10") int pageSize,
                                     @RequestParam(value = "value", required = false) String value) {

        Pageable pageable = PageRequest.of(--pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "creditId"));

        return creditService.findByNameAndMyBank(value, pageable).stream().toList();
    }

    /**
     * Просмотр кредита
     *
     * @param creditId
     * @return
     */
    @GetMapping(value = "/credit/{creditId}")
    public Credit showCredit(@PathVariable("creditId") Long creditId) {
        Credit credit;

        try {
            credit = creditService.findByCreditIdAndBankId(creditId, bankService.getMyBank().getBankId());
        } catch (CreditBaseException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }

        return credit;
    }


    /**
     * Сохранение изменений кредита
     *
     * @param credit
     * @return
     */
    @PostMapping(value = "/credit/edit/{creditId}")
    public Credit saveCredit(@Validated(Credit.class) @RequestBody Credit credit) {
        credit.setBankId(bankService.getMyBank().getBankId());

        return creditService.saveCredit(credit);
    }

    /**
     * Сохранение создания кредита
     *
     * @param credit
     * @return
     */
    @PostMapping(value = "/credit/create")
    public Credit saveCreate(@Validated(Credit.class) @RequestBody Credit credit) {
        return creditService.createCredit(credit);
    }

    /**
     * Профиль банка
     *
     * @return
     */
    @GetMapping(value = "/profile")
    public Bank bankProfile() {
        return bankService.getMyBank();
    }

    /**
     * Вывод списка клиентов банка(пользователи взявшие кредит)
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping(value = "client/all")
    public List<ClientOfBankDTO> bankClients(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                             @RequestParam(value = "size", defaultValue = "10") int pageSize) {

        Bank bank = bankService.getMyBank();
        Page<ClientOfBankDTO> clientPage = creditOfferService.findClientsOfBank(bank, pageNumber, pageSize, "userId", "asc");

        return clientPage.stream().toList();
    }

    /**
     * Вывод списка заявок на кредит
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/creditOffer/all")
    public List<CreditOffersDTO> creditOffer(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                             @RequestParam(value = "size", defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(--pageNumber, pageSize);

        Bank bank = bankService.getMyBank();
        Page<CreditOffersDTO> creditOffersDTOS = creditOfferService.findAllByBank(bank, pageable);

        return creditOffersDTOS.stream().toList();
    }

    /**
     * Ободрение заявки на кредит
     *
     * @param creditId
     * @param userId
     * @return
     */
    @GetMapping(value = "/creditOffer/credit/{creditId}/user/{userId}/approve")
    public CreditOffer approveCreditOffer(@PathVariable("creditId") Long creditId, @PathVariable("userId") Long userId) {
        Optional<CreditOffer> creditOfferOptional = creditOfferService.findById(creditId, userId);

        if (creditOfferOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit offer for user not found");
        }

        CreditOffer creditOffer = creditOfferOptional.get();
        creditOffer.setCreditOfferStatus(new CreditOfferStatus(CreditOfferStatus.StatusType.APPROVE));

        return creditOfferService.save(creditOffer);
    }

    /**
     * Отклонение заявки на кредит
     *
     * @param creditId
     * @param userId
     * @return
     */
    @GetMapping(value = "/creditOffer/credit/{creditId}/user/{userId}/reject")
    public CreditOffer rejectCreditOffer(@PathVariable("creditId") Long creditId, @PathVariable("userId") Long userId) {
        Optional<CreditOffer> creditOfferOptional = creditOfferService.findById(creditId, userId);

        if (creditOfferOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit offer for user not found");
        }

        CreditOffer creditOffer = creditOfferOptional.get();
        creditOffer.setCreditOfferStatus(new CreditOfferStatus(CreditOfferStatus.StatusType.REJECT));

        return creditOfferService.save(creditOffer);
    }
}