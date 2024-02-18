package ru.sber.edu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.edu.entity.auth.User;
import ru.sber.edu.ui.table.TableColumn;
import ru.sber.edu.ui.table.UiColumn;
import ru.sber.edu.ui.table.UiFieldType;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "favorite_credit")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(FavoriteCredit.class)
public class FavoriteCredit implements Serializable {

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "credit_id")
    private Credit credit;

    public static List<UiColumn> getColumns() {
        return List.of(
                new TableColumn("creditId", "Id"),
                new TableColumn("favoriteCredit-NameLinkToCredit", "Name", UiFieldType.CUSTOM),
                new TableColumn("favoriteCredit-delete-favorite", "", UiFieldType.CUSTOM)
        );
    }
}
