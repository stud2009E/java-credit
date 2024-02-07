package ru.sber.edu.auth;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterForm {
    @Pattern(regexp="^(?=.{6,10}$)(?![_\\d])(?!.*_{2})\\w+(?<!_)$", message="Use numbers, chars and '_'")
    private String username;

    @Size(min = 6, message = "Minimum 6 symbols")
    private String password;

    @Size(min = 6, message = "Minimum 6 symbols")
    private String confirm;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "E-mail is required")
    @Email
    private String email;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;
}
