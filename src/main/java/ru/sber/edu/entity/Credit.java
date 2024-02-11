package ru.sber.edu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "credit")
@Data
@NoArgsConstructor
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long creditId;

    @Column(name = "bank_id")
    private Long bankId;

    @NotBlank
    private String name;

    @NotNull
    private Double maxSum;

    @NotNull
    private Double rate;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateFrom;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateTo;
}
