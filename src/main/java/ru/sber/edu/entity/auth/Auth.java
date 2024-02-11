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
    private long userId;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private Role.RoleType roleName;

    private String authValue;

    /**
     * Thymeleaf security works with authorities.
     * We use them as roles in ui templates.
     * @return role name
     */
    @Override
    public String getAuthority(){
        return roleName.toString();
    }
}
