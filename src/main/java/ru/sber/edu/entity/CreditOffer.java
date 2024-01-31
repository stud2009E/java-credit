package ru.sber.edu.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Таблица кредитных предложений
 */
@Entity
@Table(name = "credit_offer")
@Data
public class CreditOffer {

    /**
     * Кредит
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "credit_id")
    private Credit credit;

    /**
     * Клиент
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    /**
     * Одобрено/отклонено
     */
    @ManyToOne
    @JoinColumn(name = "credit_offer_status")
    private CreditOfferStatus creditOfferStatus;
}
