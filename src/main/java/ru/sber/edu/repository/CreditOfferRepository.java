package ru.sber.edu.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.edu.entity.Bank;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.entity.CreditOffer;
import ru.sber.edu.entity.auth.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditOfferRepository extends JpaRepository<CreditOffer, CreditOffer> {

    @EntityGraph(value = "credit_offer-entity-graph")
    List<CreditOffer> findAllByCreditBank(Bank bank);

    List<CreditOffer> findByUserAndCredit(User user, Credit credit);

}
