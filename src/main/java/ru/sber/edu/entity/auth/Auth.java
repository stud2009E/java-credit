package ru.sber.edu.entity.auth;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authorities")
@Data
@IdClass(AuthId.class)
public class Auth implements GrantedAuthority {

    @Id
    @Column(name = "user_id")
    public long userId;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    public Role.RoleType roleName;

    public String authority;
}
