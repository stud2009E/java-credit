package ru.sber.edu.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.EntityGraph;
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

    public List<CreditOffer> findAllByBank(Bank bank){


        //List<CreditOffer> offers = creditOfferRepository.findAllByBank(bank);
        /*List<CreditOffer> offers = em.createQuery("SELECT co FROM credit_offer co " +
                //"JOIN bank AS b ON b.bank_id = co.credit.bank_id " +
                        "WHERE bank_id = :bankId ", CreditOffer.class)
                 .setParameter("bankId", bankId)
                 .setHint("javax.persistence.fetchgraph", em.getEntityGraph("credit_offer-entity-graph"))
                 .getResultList();

         */

        //List<Credit> credits = creditRepository.findByBankId(bankId);
        //List<CreditOffer> offers = creditOfferRepository.findAllByCreditIn(credits);
        //return offers;
        return null;
    };
}
