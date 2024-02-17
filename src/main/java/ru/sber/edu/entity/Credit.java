package ru.sber.edu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.sber.edu.ui.table.TableColumn;
import ru.sber.edu.ui.table.UiColumn;
import ru.sber.edu.ui.table.UiFieldType;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "credit")
@Data
@NoArgsConstructor
public class Credit {

    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "credit_sequence"),
                    @Parameter(name = "initial_value", value = "70"),
                    @Parameter(name = "increment_size", value = "1")
            })
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

    public static List<UiColumn> getColumns() {
        return Arrays.asList(
            new TableColumn("creditId", "Credit"),
            new TableColumn("credit--NameLinkToCard", "Name", UiFieldType.CUSTOM),
            new TableColumn("maxSum", "Maximum"),
            new TableColumn("rate", "Rate"),
            new TableColumn("dateFrom", "From", UiFieldType.DATE),
            new TableColumn("dateTo", "To", UiFieldType.DATE));
    }
}
