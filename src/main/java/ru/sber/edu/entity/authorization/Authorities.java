package ru.sber.edu.entity.authorization;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Таблица полномочий пользователей
 */
@Entity
@Table(name = "authorities")
@Data
public class Authorities {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_name")
    public User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "role_name")
    public Roles role;

    public  String authority;

}
