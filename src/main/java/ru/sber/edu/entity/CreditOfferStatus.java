package ru.sber.edu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "credit_offer_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
