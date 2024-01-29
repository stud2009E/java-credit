package ru.sber.edu.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Таблица клиентов
 */
@Entity
@Table(name = "client")
@Data
public class ClientEntity {

    /**
     * Ключ
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long clientId;

    /**
     * Имя клиента
     */
    private String name;
    /**
     * Телефон клиента
     */
    private String phone;

    /**
     * Паспортные данные клиента
     */
    private String passport;
}
