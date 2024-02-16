package ru.sber.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.edu.entity.*;
import ru.sber.edu.entity.auth.User;
import ru.sber.edu.exception.CreditBaseException;
import ru.sber.edu.repository.CreditFavoriteRepository;
import ru.sber.edu.repository.CreditOfferRepository;
import ru.sber.edu.repository.CreditRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CreditService {

    @Autowired
    private CreditFavoriteRepository creditFavoriteRepository;

    @Autowired
    private CreditOfferRepository creditOfferRepository;

    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private BankService bankService;

    @Autowired
    private UserService userService;


    public Page<Credit> findByBank(Bank bank, int pageNumber, int pageSize, String sortedBy, String order) throws NullPointerException {

        Sort sorting = Sort.by(sortedBy);
        Pageable paging = PageRequest.of(--pageNumber, pageSize, order.equals("acs") ? sorting.ascending() : sorting.descending());

        return creditRepository.findByBank(bank, paging);
    }


    public Page<Credit> findByNameAndBankId(String name, Bank bank, int pageNumber, int pageSize, String sortedBy, String order) throws NullPointerException {

        Sort sorting = Sort.by(sortedBy);
        Pageable paging = PageRequest.of(--pageNumber, pageSize, order.equals("acs") ? sorting.ascending() : sorting.descending());

        return creditRepository.findByBankAndNameContainingIgnoreCase(bank, name, paging);
    }


    public Credit createCredit(Credit credit, Long bankId) {
        Bank bank = new Bank();
        bank.setBankId(bankId);

        credit.setBank(bank);
        return creditRepository.saveAndFlush(credit);
    }


    public Optional<Credit> findById(Long creditId) {
        return creditRepository.findById(creditId);
    }


    public Credit saveCredit(Credit credit) {
        return creditRepository.saveAndFlush(credit);
    }


    public Optional<Bank> findBankById(Long bankId) {
        return bankService.findById(bankId);
    }


    public Page<Credit> findAll(Pageable pageable) {
        return creditRepository.findAll(pageable);
    }


    @Transactional
    public CreditOffer createCreditOffer(Credit credit) {
        Optional<Credit> creditOptional = findById(credit.getCreditId());

        if (creditOptional.isEmpty()) {
            throw new CreditBaseException("Unable to find credit!");
        }

        User user = userService.getUser();
        List<CreditOffer> existedOffer = creditOfferRepository.findByUserAndCredit(user, creditOptional.get());

        if (!existedOffer.isEmpty()){
            throw new CreditBaseException("Request for credit exist!");
        }

        CreditOffer creditOffer = new CreditOffer();
        creditOffer.setCredit(creditOptional.get());
        creditOffer.setUser(user);
        creditOffer.setCreditOfferStatus(new CreditOfferStatus(CreditOfferStatus.StatusType.REQUEST));

        return creditOfferRepository.save(creditOffer);
    }
    public FavoriteCredit addFavoriteCredit(FavoriteCredit favoriteCredit){
        return creditFavoriteRepository.save(favoriteCredit);
    }

    public void removeFavoriteCredit(FavoriteCredit favoriteCredit){
        creditFavoriteRepository.delete(favoriteCredit);
    }

    public List<FavoriteCredit> findFavoriteCredit(User user, Credit credit){
        return creditFavoriteRepository.findByUserAndCredit(user, credit);
    }

    public Optional<Credit> findByIdAndBank(Long creditId, Bank bank){
        Optional<Credit> creditOptional = creditRepository.findByCreditIdAndBank(creditId, bank);

        if (creditOptional.isEmpty()){
            throw new CreditBaseException("Unable to find credit!");
        }
        return creditOptional;
    }
}