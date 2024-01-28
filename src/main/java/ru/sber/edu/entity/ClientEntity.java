package ru.sber.edu.entity;

import jakarta.persistence.*;

/**
 * Таблица клиентов
 */
@Entity
@Table(name = "client")
public class ClientEntity {

    /**
     * Ключ
     */
    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long clientId;

    /**
     * Имя клиента
     */
    @Column(name = "name")
    private String name;
    /**
     * Телефон клиента
     */
    @Column(name = "phone")
    private String phone;

    /**
     * Паспортные данные клиента
     */
    @Column(name = "passport")
    private String passport;

    public ClientEntity() {
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public Long getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassport() {
        return passport;
    }
}
