package ru.sber.edu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import ru.sber.edu.entity.auth.User;

import java.util.List;

@Entity
@Table(name = "bank")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "bank_id")
    public Long bankId;

    private String name;

    @OneToMany()
    @JoinColumn(name = "bank_id")
    public List<BankUser> users;

    @OneToMany
    @JoinColumn(name = "bank_id")
    private List<Credit> credits;
}
