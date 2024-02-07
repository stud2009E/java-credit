package ru.sber.edu.controller.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sber.edu.service.UserService;

import java.util.Objects;

@Controller
@RequestMapping
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String getLoginForm(Model model){
        if (userService.isLogged()){
            return "redirect:/";
        }

        model.addAttribute("isLogged", userService.isLogged());
        return "loginForm";
    }

    @GetMapping("/login-error")
    public String loginError(HttpServletRequest request, Model model){
        model.addAttribute("isLogged", userService.isLogged());

        HttpSession session = request.getSession(false);

        if(Objects.nonNull(session)){
            AuthenticationException ex = (AuthenticationException)session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (Objects.nonNull(ex)){
                model.addAttribute("errorMessage", ex.getMessage());
            }
        }

        return "loginForm";
    }

    @GetMapping("/logout")
    public String logoutForm(Model model){
        model.addAttribute("isLogged", userService.isLogged());

        if (!userService.isLogged()){
            return "redirect:/";
        }

        return "logoutForm";
    }
}
