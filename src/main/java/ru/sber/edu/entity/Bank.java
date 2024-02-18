package ru.sber.edu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.edu.entity.auth.User;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "bank")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bank implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "bank_id")
    private Long bankId;

    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private List<Credit> credits;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "bank_user",
            joinColumns = @JoinColumn(name = "bank_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false, unique = true)
    )
    private List<User> bankUsers;
}
