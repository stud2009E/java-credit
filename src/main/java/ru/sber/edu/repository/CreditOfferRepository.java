package ru.sber.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sber.edu.entity.Bank;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.entity.CreditOffer;

import java.util.List;

@Repository
public interface CreditOfferRepository extends JpaRepository<CreditOffer, CreditOffer> {
    List<CreditOffer> findAllByCreditIn(List<Credit> credits);
}
