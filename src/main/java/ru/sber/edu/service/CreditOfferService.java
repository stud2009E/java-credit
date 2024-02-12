package ru.sber.edu.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import ru.sber.edu.entity.Bank;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.entity.CreditOffer;
import ru.sber.edu.repository.BankRepository;
import ru.sber.edu.repository.CreditOfferRepository;
import ru.sber.edu.repository.CreditRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CreditOfferService {

    @Autowired
    private CreditOfferRepository creditOfferRepository;

    @Autowired
    private CreditRepository creditRepository;

    @PersistenceContext
    private EntityManager em;

    public List<CreditOffer> findAllByBank(Long bankId){



        //List<Credit> credits = creditRepository.findByBankId(bankId);
        //List<CreditOffer> offers = creditOfferRepository.findAllByCreditIn(credits);
        //return offers;


        return null;
    };
}
