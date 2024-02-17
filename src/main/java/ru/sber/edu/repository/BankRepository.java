package ru.sber.edu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sber.edu.entity.Bank;
import ru.sber.edu.projection.ClientOfBank;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

    @Query(value = "SELECT bu.user_id, u.email, u.phone, u.first_name, u.last_name "
                    + "FROM bank_user bu "
                    + "INNER JOIN users AS u on u.user_id = bu.user_id "
                    + "WHERE bu.bank_id = :bankId"
                    , nativeQuery = true
                    )
    Page<ClientOfBank> findClientsByBankId(@Param("bankId") Long bankId, Pageable pageable);
}
