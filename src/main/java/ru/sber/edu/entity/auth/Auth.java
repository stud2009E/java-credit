package ru.sber.edu.entity.auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Entity
@Table(name = "authorities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auth implements GrantedAuthority, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "auth_id")
    private long authId;

    @Column(name = "user_id")
    private long userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_name")
    private Role role;

    /**
     * Thymeleaf security works with authorities.
     * We use them as roles in ui templates.
     * @return role name
     */
    @Override
    public String getAuthority(){
        return role.getRoleName().toString();
    }
}
