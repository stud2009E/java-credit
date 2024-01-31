package ru.sber.edu.entityAuthorazation;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Таблица полномочий пользователей
 */
@Entity
@Table(name = "authorities")
@Data
public class AuthoritiesEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_name")
    public UserEntity user;

    @Id
    @ManyToOne
    @JoinColumn(name = "role_name")
    public RolesEntity role;

    public  String authority;

}
