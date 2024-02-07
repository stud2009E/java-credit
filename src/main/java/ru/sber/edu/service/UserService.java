package ru.sber.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import ru.sber.edu.auth.RegisterForm;
import ru.sber.edu.entity.Client;
import ru.sber.edu.entity.auth.Auth;
import ru.sber.edu.entity.auth.Role;
import ru.sber.edu.entity.auth.User;
import ru.sber.edu.repository.AuthRepository;
import ru.sber.edu.repository.ClientRepository;
import ru.sber.edu.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void saveClient(RegisterForm form) {
        Client client = clientRepository.save(new Client());

        User user = userFromForm(form);
        User savedUser = userRepository.save(user);

        Auth auth = new Auth();
        auth.setUserId(savedUser.getUserId());
        auth.setRoleName(Role.RoleType.CLIENT);
        auth.setAuthority(String.valueOf(client.getClientId()));

        authRepository.save(auth);
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

    public void checkClientData(RegisterForm form, Errors errors) {
        if (!form.getPassword().equals(form.getConfirm())) {
            errors.rejectValue("confirm", "confirm", "Password fields are not equal");
        }

        if (errors.hasErrors()){
            return;
        }

        User user = userRepository.findByUsername(form.getUsername());
        if (Objects.nonNull(user)){
            errors.rejectValue("username", "username", "Username is busy");
        }
    }

    public Role.RoleType getRole(){
        return Objects.requireNonNull(getAuth()).getRoleName();
    }

    public long getAuthority(){
        return Long.parseLong(Objects.requireNonNull(getAuth()).getAuthority());
    }


    public boolean isLogged(){
        Authentication auth = getAuthentication();
        return auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated();
    }


    private Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private Auth getAuth(){
        List<Auth> list = (List<Auth>) getAuthentication().getAuthorities().stream().toList();
        if (list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
}