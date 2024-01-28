package ru.sber.edu.entity;

import jakarta.persistence.*;

/**
 * Таблица избранных кредитов
 */
@Entity
@Table(name = "favorite_credit")
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

    public FavoriteCreditEntity() {
    }

    public ClientEntity getClient() {
        return client;
    }

    public CreditEntity getCredit() {
        return credit;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public void setCredit(CreditEntity credit) {
        this.credit = credit;
    }
}
