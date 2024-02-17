package ru.sber.edu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.entity.CreditOffer;
import ru.sber.edu.entity.auth.User;
import ru.sber.edu.projection.ClientOfBankDTO;
import ru.sber.edu.projection.CreditOffersDTO;

import java.util.List;

@Repository
public interface CreditOfferRepository extends JpaRepository<CreditOffer, Long> {
    @EntityGraph(value = "credit_offer-entity-graph")
    @Query(value = "SELECT new ru.sber.edu.projection.CreditOffersDTO(" +
            "co.credit.creditId, " +
            "co.credit.name, " +
            "co.user.userId, " +
            "co.user.firstName, " +
            "co.user.lastName, " +
            "co.creditOfferStatus.statusName) " +
            "FROM CreditOffer co " +
            "WHERE co.credit.bankId = :bankId"
    )
    Page<CreditOffersDTO> findAllCreditOffersByBankId(@Param("bankId") Long bankId, Pageable pageable);


    @EntityGraph(value = "credit_offer-entity-graph")
    List<CreditOffer> findAllByCreditBankId(Long bankId);


    List<CreditOffer> findByUserAndCredit(User user, Credit credit);


    @Query(value = "SELECT new ru.sber.edu.projection.ClientOfBankDTO( " +
            "co.user.userId, " +
            "co.user.firstName, " +
            "co.user.lastName, " +
            "co.user.phone, " +
            "co.user.email) " +
            "FROM CreditOffer co " +
            "WHERE co.credit.bankId = :bankId " +
            "AND co.creditOfferStatus.statusName = 'APPROVE'"
    )
    Page<ClientOfBankDTO> findClientsOfBank(@Param("bankId") Long bankId, Pageable pageable);
}