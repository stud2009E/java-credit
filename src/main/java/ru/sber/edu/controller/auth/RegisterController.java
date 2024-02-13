package ru.sber.edu.controller.auth;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.sber.edu.auth.RegisterForm;
import ru.sber.edu.service.UserService;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        model.addAttribute("isLogged", userService.isLogged());

        if (userService.isLogged()) {
            return "redirect:/";
        }

        return "registerForm";
    }

    @PostMapping
    public String processRegistration(@Valid RegisterForm form, Errors errors, Model model) {
        if (userService.isLogged()) {
            return "redirect:/";
        }

        model.addAttribute("isLogged", userService.isLogged());

        userService.checkRegisterData(form, errors);
        if (errors.hasErrors()) {
            return "registerForm";
        }

        userService.saveClient(form);

        return "redirect:/login";
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public String handle(){
        return "error/500";
    }

}
