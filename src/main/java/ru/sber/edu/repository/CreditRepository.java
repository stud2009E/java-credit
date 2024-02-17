package ru.sber.edu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.edu.entity.Bank;
import ru.sber.edu.entity.Credit;

import java.util.Optional;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {
    Page<Credit> findByBankId(long bankId, Pageable pageable);

    Page<Credit> findByBankIdAndNameContainingIgnoreCase(Long bankId, String name, Pageable pageable);

    Optional<Credit> findByCreditId(Long creditId);

    Page<Credit> findAll(Pageable pageable);

    Optional<Credit> findByCreditIdAndBankId(Long creditId, Long bankId);
}
