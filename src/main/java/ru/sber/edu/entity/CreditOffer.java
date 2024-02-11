package ru.sber.edu.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.edu.entity.auth.User;

@Entity
@Table(name = "credit_offer")
@Data
@NoArgsConstructor
public class CreditOffer {

    @Id
    @ManyToOne
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "status_name")
    private CreditOfferStatus creditOfferStatus;
}
