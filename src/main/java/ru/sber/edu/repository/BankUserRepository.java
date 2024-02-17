package ru.sber.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.edu.entity.BankUser;

@Repository
public interface BankUserRepository extends JpaRepository<BankUser, Long> {
}
