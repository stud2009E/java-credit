package ru.sber.edu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.entity.FavoriteCredit;
import ru.sber.edu.entity.auth.User;

import java.util.List;

@Repository
public interface CreditFavoriteRepository extends JpaRepository<FavoriteCredit, FavoriteCredit> {

    List<FavoriteCredit> findByUserAndCredit(User user, Credit credit);

    Page<FavoriteCredit> findByUser(User user, Pageable pageable);

}
