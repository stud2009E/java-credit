package ru.sber.edu.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Таблица кредитных предложений
 */
@Entity
@Table(name = "credit_offer")
@Data
public class CreditOfferEntity {

    /**
     * Кредит
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "credit_id")
    private CreditEntity credit;

    /**
     * Клиент
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    /**
     * Одобрено/отклонено
     */
    private boolean approved;
}
