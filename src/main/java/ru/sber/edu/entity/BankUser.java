package ru.sber.edu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.edu.entity.auth.User;

@Entity
@Table(name = "bank_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(BankUser.class)
public class BankUser {

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;
}
