package ru.sber.edu.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "credit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long creditId;

    @Column(name = "bank_id")
    private Long bankId;

    private String name;

    private Float maxSum;

    private double rate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateTo;

}
