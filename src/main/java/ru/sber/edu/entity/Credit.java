package ru.sber.edu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    @Column(name = "bank_id")
    private Long bankId;

    @NotBlank
    private String name;

    @NotBlank
    private double maxSum;

    @NotBlank
    private double rate;

    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateFrom;

    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateTo;

}
