package ru.sber.edu.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * Таблица кредитов
 */
@Entity
@Table(name = "credit")
public class CreditEntity {

    @Basic
    private java.sql.Date sqlDate;

    /**
     * Ключ
     */
    @Id
    @Column(name = "credit_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long creditId;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private BankEntity bankId;

    @Column(name = "name")
    private String name;

    @Column(name = "max_sum")
    private Float maxSum;

    @Column(name = "rate")
    private double rate;

    @Column(name = "date_from")
    private LocalDateTime dateFrom;

    @Column(name = "date_to")
    private LocalDateTime dateTo;

    public CreditEntity() {
    }

    public Date getSqlDate() {
        return sqlDate;
    }

    public Long getCreditId() {
        return creditId;
    }

    public BankEntity getBankId() {
        return bankId;
    }

    public String getName() {
        return name;
    }

    public Float getMaxSum() {
        return maxSum;
    }

    public double getRate() {
        return rate;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public void setSqlDate(Date sqlDate) {
        this.sqlDate = sqlDate;
    }

    public void setCreditId(Long creditId) {
        this.creditId = creditId;
    }

    public void setBank(BankEntity bankId) {
        this.bankId = bankId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLimit(Float maxSum) {
        this.maxSum = maxSum;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }
}
