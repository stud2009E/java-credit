package ru.sber.edu.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Таблица избранных кредитов
 */
@Entity
@Table(name = "favorite_credit")
@Data
public class FavoriteCreditEntity {

    /**
     * Клиент
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    /**
     * Кредит
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "credit_id")
    private CreditEntity credit;
}
