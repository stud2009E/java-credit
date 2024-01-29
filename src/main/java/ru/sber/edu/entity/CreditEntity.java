package ru.sber.edu.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * Таблица кредитов
 */
@Entity
@Table(name = "credit")
@Data
public class CreditEntity {

    @Basic
    private java.sql.Date sqlDate;

    /**
     * Ключ
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long creditId;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private BankEntity bankId;

    private String name;

    private Float maxSum;

    private double rate;

    private LocalDateTime dateFrom;

    private LocalDateTime dateTo;

}
