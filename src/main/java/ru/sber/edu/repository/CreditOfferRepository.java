package ru.sber.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.edu.entity.CreditOffer;
import ru.sber.edu.entity.ids.CreditOfferId;

@Repository
public interface CreditOfferRepository extends JpaRepository<CreditOffer, CreditOfferId> {

}
