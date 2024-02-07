package ru.sber.edu.entity.auth;

import java.io.Serializable;

public class AuthId implements Serializable {
    public long userId;
    public Role.RoleType roleName;
}