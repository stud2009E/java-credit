package ru.sber.edu.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Таблица избранных кредитов
 */
@Entity
@Table(name = "favorite_credit")
@Data
public class FavoriteCredit {

    /**
     * Клиент
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    /**
     * Кредит
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "credit_id")
    private Credit credit;
}
