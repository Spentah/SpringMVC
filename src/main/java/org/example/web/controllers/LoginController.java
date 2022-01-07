package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.LoginService;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "login")
public class LoginController {

    private Logger log = Logger.getLogger(LoginController.class);
    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String home(Model model) {
        log.info("GET request to '/login'");
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping(value = "auth")
    public String authenticate(LoginForm loginForm) {
        if (loginService.authenticate(loginForm)) {
            log.info("login success, redirect to /books/shelf");
            return "redirect:/books/shelf";
        } else {
            log.info("wrong login, redirect to /login");
            return "redirect:/login";
        }
    }
}
