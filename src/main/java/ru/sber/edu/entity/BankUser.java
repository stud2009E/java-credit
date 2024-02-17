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
public class BankUser {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "bank_id")
    private Long bankId;
}
