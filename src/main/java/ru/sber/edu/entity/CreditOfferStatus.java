package ru.sber.edu.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "credit_offer_status")
@Data
public class CreditOfferStatus {


    @Id
    @Column(name = "status_name")
    @Enumerated(EnumType.STRING)
    public StatusType statusName;

    public enum StatusType{
        REQUEST,
        APPROVE,
        REJECT
    }

}
