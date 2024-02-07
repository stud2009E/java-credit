package ru.sber.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.edu.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
