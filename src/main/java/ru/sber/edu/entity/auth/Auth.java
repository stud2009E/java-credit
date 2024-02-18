package ru.sber.edu.entity.auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Entity
@Table(name = "authorities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auth implements GrantedAuthority, Serializable {
    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "auth_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "70"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            })
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
