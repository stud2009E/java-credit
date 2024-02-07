package ru.sber.edu.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "favorite_credit")
@Data
public class FavoriteCredit {

    @Id
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Id
    @ManyToOne
    @JoinColumn(name = "credit_id")
    private Credit credit;
}
