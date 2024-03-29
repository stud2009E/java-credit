package ru.sber.edu.service;

import lombok.Data;
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
@Data
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


    public Page<Credit> findByMyBank(Pageable paging) throws NullPointerException {
        return creditRepository.findByBankId(bankService.getMyBank().getBankId(), paging);
    }


    public Page<Credit> findByNameAndMyBank(String name, Pageable paging) throws NullPointerException {
        return creditRepository.findByBankIdAndNameContainingIgnoreCase(bankService.getMyBank().getBankId(), name, paging);
    }

    public Credit createCredit(Credit credit) {
        credit.setBankId(bankService.getMyBank().getBankId());
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

    public Page<CreditOffer> findCreditOfferByUser(User user, Pageable pageable){
        return creditOfferRepository.findByUser(user, pageable);
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

    public Page<FavoriteCredit> findFavoriteCreditByUser(User user, Pageable pageable){
        return creditFavoriteRepository.findByUser(user, pageable);
    }


    public Credit findByCreditIdAndBankId(Long creditId, Long bankId) {
        Optional<Credit> creditOptional = creditRepository.findByCreditIdAndBankId(creditId, bankId);

        if (creditOptional.isEmpty()){
            throw new CreditBankException("Credit does not belongs to bank");
        }

        return creditOptional.get();
    }


    public Page<Credit> findByNameContainingIgnoreCase(String name, Pageable pageable){
        return creditRepository.findByNameContainingIgnoreCase(name, pageable);
    }
}