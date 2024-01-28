package ru.sber.edu.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDateTime;

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
    private BankEntity bank;

    @Column(name = "name")
    private String name;

    @Column(name = "limit")
    private Float limit;

    @Column(name = "rate")
    private double rate;

    @Column(name = "from")
    private LocalDateTime from;

    @Column(name = "to")
    private LocalDateTime to;

    public CreditEntity() {
    }

    public Date getSqlDate() {
        return sqlDate;
    }

    public Long getCreditId() {
        return creditId;
    }

    public BankEntity getBank() {
        return bank;
    }

    public String getName() {
        return name;
    }

    public Float getLimit() {
        return limit;
    }

    public double getRate() {
        return rate;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setSqlDate(Date sqlDate) {
        this.sqlDate = sqlDate;
    }

    public void setCreditId(Long creditId) {
        this.creditId = creditId;
    }

    public void setBank(BankEntity bank) {
        this.bank = bank;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLimit(Float limit) {
        this.limit = limit;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }
}
