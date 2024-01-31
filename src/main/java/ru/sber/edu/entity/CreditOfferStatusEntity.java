package ru.sber.edu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "credit_offer_status")
@Data
public class CreditOfferStatusEntity {

    @Id
    @Column(length = 2)
    public String creditOfferStatus;

    public String creditOfferStatusName;

}
