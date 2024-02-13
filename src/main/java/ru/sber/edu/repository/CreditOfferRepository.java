package ru.sber.edu.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sber.edu.entity.Bank;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.entity.CreditOffer;

import java.util.List;

@Repository
public interface CreditOfferRepository extends JpaRepository<CreditOffer, CreditOffer> {
    @EntityGraph(value = "credit_offer-entity-graph")
   List<CreditOffer> findAllByBank(Bank bank);
}
