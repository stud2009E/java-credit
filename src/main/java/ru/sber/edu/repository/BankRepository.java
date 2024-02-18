package ru.sber.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.edu.entity.Bank;


@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {}
