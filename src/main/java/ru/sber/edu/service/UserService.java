package ru.sber.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import ru.sber.edu.auth.RegisterForm;
import ru.sber.edu.entity.auth.Auth;
import ru.sber.edu.entity.auth.Role;
import ru.sber.edu.entity.auth.User;
import ru.sber.edu.repository.AuthRepository;
import ru.sber.edu.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void create(RegisterForm form) {
        User user = userFromForm(form);
        User savedUser = userRepository.save(user);

        Auth auth = new Auth();
        auth.setUserId(savedUser.getUserId());
        auth.setRole(new Role(Role.RoleType.CLIENT));

        authRepository.save(auth);
    }

    @Transactional
    public void update(User user) {
        userRepository.save(user);
    }


    private User userFromForm(RegisterForm form) {
        User user = new User();

        user.setUsername(form.getUsername());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setCreateDate(LocalDateTime.now());
        user.setChangeDate(LocalDateTime.now());

        user.setEmail(form.getEmail());
        user.setPhone(form.getPhone());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());

        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        return user;
    }

    public void checkRegisterData(RegisterForm form, Errors errors) {
        if (!form.getPassword().equals(form.getConfirm())) {
            errors.rejectValue("confirm", "confirm", "Password fields are not equal");
        }

        if (errors.hasErrors()) {
            return;
        }

        User user = userRepository.findByUsername(form.getUsername());
        if (Objects.nonNull(user)) {
            errors.rejectValue("username", "username", "Username is busy");
        }
    }


    public Collection<? extends GrantedAuthority> getRole() {
        return getAuthentication().getAuthorities();
    }


    public boolean isLogged() {
        Authentication auth = getAuthentication();
        return auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated();
    }


    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    public User getUser() {
        return (User)getAuthentication().getPrincipal();
    }
}