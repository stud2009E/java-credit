package ru.sber.edu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
