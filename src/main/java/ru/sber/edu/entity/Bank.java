package ru.sber.edu.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Таблица Банков
 */
@Entity
@Table(name = "bank")
@Data
public class Bank {

    /**
     * Ключ
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long bankId;

    /**
     * Название банка
     */
    private String name;
}
