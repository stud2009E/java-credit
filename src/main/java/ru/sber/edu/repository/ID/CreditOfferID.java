package ru.sber.edu.repository.ID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sber.edu.entity.Credit;
import ru.sber.edu.entity.auth.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditOfferID {
    public Credit credit;
    public User user;
}
