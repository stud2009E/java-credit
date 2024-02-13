package ru.sber.edu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.edu.entity.auth.User;

@Entity
@Table(name = "favorite_credit")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(FavoriteCredit.class)
public class FavoriteCredit {

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "credit_id")
    private Credit credit;
}
