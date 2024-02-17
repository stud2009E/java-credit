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
import ru.sber.edu.exception.CreditBankException;
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

        return creditRepository.findByBankId(bank.getBankId(), paging);
    }


    public Page<Credit> findByNameAndBank(String name, Bank bank, int pageNumber, int pageSize, String sortedBy, String order) throws NullPointerException {

        Sort sorting = Sort.by(sortedBy);
        Pageable paging = PageRequest.of(--pageNumber, pageSize, order.equals("acs") ? sorting.ascending() : sorting.descending());

        return creditRepository.findByBankIdAndNameContainingIgnoreCase(bank.getBankId(), name, paging);
    }

    public Credit createCredit(Credit credit, Bank bank) {
        credit.setBankId(bank.getBankId());
        return creditRepository.saveAndFlush(credit);
    }


    public Credit findById(Long creditId) throws CreditBankException {
        Optional<Credit> creditOptional = creditRepository.findById(creditId);

        if (creditOptional.isEmpty()){
            throw new CreditBankException("Credit " + creditId + " does not exist");
        }

        return creditOptional.get();
    }


    public Credit saveCredit(Credit credit) {
        return creditRepository.saveAndFlush(credit);
    }


    public Page<Credit> findAll(Pageable pageable) {
        return creditRepository.findAll(pageable);
    }


    @Transactional
    public CreditOffer createCreditOffer(Credit credit) {
        credit = findById(credit.getCreditId());

        User user = userService.getUser();
        List<CreditOffer> existedOffer = creditOfferRepository.findByUserAndCredit(user, credit);

        if (!existedOffer.isEmpty()){
            throw new CreditBaseException("Request for credit exist!");
        }

        CreditOffer creditOffer = new CreditOffer();
        creditOffer.setCredit(credit);
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


    public Credit findByCreditIdAndBankId(Long creditId, Long bankId) {
        Optional<Credit> creditOptional = creditRepository.findByCreditIdAndBankId(creditId, bankId);

        if (creditOptional.isEmpty()){
            throw new CreditBankException("Credit does not belongs to bank");
        }

        return creditOptional.get();
    }
}