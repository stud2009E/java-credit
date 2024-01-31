package ru.sber.edu.entity.authorization;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Таблица ролей
 */
@Entity
@Table(name = "roles")
@Data
public class RolesEntity {

    @Id
    @Column(length = 10)
    public String roleName;

}
