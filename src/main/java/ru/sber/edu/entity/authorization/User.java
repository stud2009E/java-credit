package ru.sber.edu.entity.authorization;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Таблица пользователей сервисом(клиенты, банки, админы)
 */
@Entity
@Table(name = "\"user\"")
@Data
public class User {

    @Id
    @Column(length = 16)
    public String userName;
    @Column(length = 10)
    public String password;
    @ManyToOne
    @JoinColumn(name = "role_name")
    public Roles role;
    private LocalDateTime createDate;
    private LocalDateTime changeDate;
}