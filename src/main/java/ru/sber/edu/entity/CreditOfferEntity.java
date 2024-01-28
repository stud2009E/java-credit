package ru.sber.edu.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "credit_offer")
public class CreditOfferEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "credit_id")
    private CreditEntity credit;

    @Id
    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @Column(name = "approved", length = 1)
    private boolean approved;

    public CreditOfferEntity() {
    }

    public void setCredit(CreditEntity credit) {
        this.credit = credit;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public CreditEntity getCredit() {
        return credit;
    }

    public ClientEntity getClient() {
        return client;
    }

    public boolean isApproved() {
        return approved;
    }
}
