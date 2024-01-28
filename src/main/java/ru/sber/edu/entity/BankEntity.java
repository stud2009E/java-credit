package ru.sber.edu.entity;

import jakarta.persistence.*;

/**
 * Таблица Банков
 */
@Entity
@Table(name = "bank")
public class BankEntity {

    /**
     * Ключ
     */
    @Id
    @Column(name = "bank_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer bankId;

    /**
     * Название банка
     */
    @Column(name = "name")
    private String name;

    public BankEntity() {
    }

    public Integer getBankId() {
        return bankId;
    }

    public String getName() {
        return name;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
