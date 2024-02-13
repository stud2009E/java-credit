package ru.sber.edu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sber.edu.entity.Bank;
import ru.sber.edu.entity.Credit;

import java.util.List;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {
    Page<Credit> findByBank(Bank bank, Pageable pageable);
    //List<Credit> findByBankId(Long bankId);
    Page<Credit> findByBankAndNameContainingIgnoreCase(Bank bank, String name, Pageable pageable);



}
