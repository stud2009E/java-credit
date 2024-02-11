package ru.sber.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.edu.entity.auth.Auth;

public interface AuthRepository extends JpaRepository<Auth, Long> {
}
