package ru.sber.edu.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "bank")
@Data
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long bankId;

    private String name;
}
