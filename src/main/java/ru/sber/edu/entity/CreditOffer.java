package ru.sber.edu.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "credit_offer")
@Data
public class CreditOffer {

    @Id
    @ManyToOne
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @Id
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "credit_offer_status")
    private CreditOfferStatus creditOfferStatus;
}
