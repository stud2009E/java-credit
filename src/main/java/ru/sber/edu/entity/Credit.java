package ru.sber.edu.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "credit")
@Data
public class Credit {

    @Basic
    private java.sql.Date sqlDate;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long creditId;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bankId;

    private String name;

    private Float maxSum;

    private double rate;

    private LocalDateTime dateFrom;

    private LocalDateTime dateTo;

}
