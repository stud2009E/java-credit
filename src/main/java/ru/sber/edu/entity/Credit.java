package ru.sber.edu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.sber.edu.ui.table.FieldType;
import ru.sber.edu.ui.table.TableColumn;
import ru.sber.edu.ui.table.UiColumn;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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


    public static List<UiColumn> getColumns(){
        return Arrays.asList(
                new TableColumn("creditId", "Credit"),
                new TableColumn("bankId", "Bank"),
                new TableColumn("name", "Name"),
                new TableColumn("maxSum", "Maximum"),
                new TableColumn("rate", "Rate"),
                new TableColumn("dateFrom", "From", FieldType.DATE),
                new TableColumn("dateTo", "To", FieldType.DATE));
    }
}
