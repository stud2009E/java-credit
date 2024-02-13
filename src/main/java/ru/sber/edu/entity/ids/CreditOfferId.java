package ru.sber.edu.entity.ids;

import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.edu.entity.CreditOffer;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditOfferId {

    private long creditId;

    private long userId;
}
