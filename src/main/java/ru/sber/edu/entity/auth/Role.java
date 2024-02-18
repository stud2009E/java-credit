package ru.sber.edu.entity.auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {

    @Id
    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private RoleType roleName;

    public enum RoleType{
        ADMIN,
        CLIENT,
        BANK
    }

}