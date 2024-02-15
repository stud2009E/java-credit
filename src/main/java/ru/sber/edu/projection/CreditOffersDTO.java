package ru.sber.edu.projection;

import jakarta.persistence.IdClass;
import jakarta.persistence.metamodel.SingularAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import ru.sber.edu.entity.Bank;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.entity.CreditOffer;
import ru.sber.edu.entity.CreditOfferStatus;
import ru.sber.edu.entity.auth.User;
import ru.sber.edu.ui.table.TableColumn;
import ru.sber.edu.ui.table.UiColumn;
import ru.sber.edu.ui.table.UiFieldType;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public class CreditOffersDTO {

    public Long creditId;
    public String creditName;
    public Long userId;
    public String firstName;
    public String lastName;
    public CreditOfferStatus.StatusType statusName;

    public static List<UiColumn> getColumns() {
        return Arrays.asList(
                new TableColumn("creditId", "Credit"),
                new TableColumn("creditOffer--LinkToCreditOffer", "Credit name", UiFieldType.CUSTOM),
                new TableColumn("userId", "User"),
                new TableColumn("firstName", "Name"),
                new TableColumn("lastName", "Last name"),
                new TableColumn("statusName", "Status")
        );
    }

}
