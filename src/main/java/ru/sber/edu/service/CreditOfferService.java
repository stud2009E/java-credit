package ru.sber.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.sber.edu.entity.Bank;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.entity.CreditOffer;
import ru.sber.edu.entity.auth.User;
import ru.sber.edu.exception.CreditBaseException;
import ru.sber.edu.projection.CreditOffersDTO;
import ru.sber.edu.repository.CreditOfferRepository;
import ru.sber.edu.repository.ID.CreditOfferID;

import java.util.Optional;

@Service
public class CreditOfferService {

    @Autowired
    private CreditOfferRepository creditOfferRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CreditService creditService;

    public Page<CreditOffersDTO> findAllByBank(Bank bank, Pageable pageable){

        return creditOfferRepository.findAllCreditOffersByBankId(bank.getBankId(), pageable);
    };

    public Optional<CreditOffer> findById(Long creditId, Long userId){

        Credit credit = new Credit();
        credit.setCreditId(creditId);

        User user = new User();
        user.setUserId(userId);

        CreditOfferID creditOfferRepositoryID = new CreditOfferID(credit, user);
        Optional<CreditOffer> creditOffer = creditOfferRepository.findById(creditOfferRepositoryID);

        if (creditOffer.isEmpty()){
            throw new NullPointerException("There are no credit offer by " +
                    "creditId= " + creditId +
                    "and userId= " + userId);
        }
        return creditOffer;
    }

    public void save(CreditOffer creditOffer){

        Optional<Credit> creditOptional = creditService.findByIdAndBankId(creditOffer.getCredit().getCreditId(), creditOffer.getCredit().getBank());
        if (creditOptional.isEmpty()) {
            throw new CreditBaseException("Unable to find credit!");
        }

        creditOfferRepository.saveAndFlush(creditOffer);
    }
}
