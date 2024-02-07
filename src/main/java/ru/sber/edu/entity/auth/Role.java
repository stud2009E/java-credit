package ru.sber.edu.entity.auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @Column(name = "role_name",length = 10)
    @Enumerated(EnumType.STRING)
    private RoleType roleName;


    @OneToMany
    @JoinColumn(name = "role_name")
    private List<Auth> authorities;

    public enum RoleType{
        ADMIN,
        CLIENT,
        BANK
    }

}