package ru.sber.edu.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.edu.entity.auth.User;

@Entity
@Table(name = "credit_offer")
@Data
@NoArgsConstructor
@IdClass(CreditOffer.class)
public class CreditOffer {

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "status_name")
    private CreditOfferStatus creditOfferStatus;
}
