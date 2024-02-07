package ru.sber.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sber.edu.service.UserService;

@Controller
public class AppController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    public String main(Model model){
        model.addAttribute("isLogged", userService.isLogged());

        return "home";
    }
}