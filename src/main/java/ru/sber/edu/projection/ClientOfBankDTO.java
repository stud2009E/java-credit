package ru.sber.edu.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.sber.edu.ui.table.TableColumn;
import ru.sber.edu.ui.table.UiColumn;
import ru.sber.edu.ui.table.UiFieldType;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public class ClientOfBankDTO {

    public Long userId;
    public String firstName;
    public String lastName;
    public String phone;
    public String email;

    public static List<UiColumn> getColumns() {
        return Arrays.asList(
                new TableColumn("userId", "User"),
                //new TableColumn("creditOffer--LinkToCreditOffer", "Credit name", UiFieldType.CUSTOM),
                new TableColumn("firstName", "First name"),
                new TableColumn("lastName", "Last name"),
                new TableColumn("phone", "Phone"),
                new TableColumn("email", "Email")
        );
    }

}
