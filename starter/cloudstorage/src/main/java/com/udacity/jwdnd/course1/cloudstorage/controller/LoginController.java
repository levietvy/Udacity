package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {


    public LoginController() {
    }

    @GetMapping
    public String loginPage(Model model, HttpSession httpSession){
        model.addAttribute("signupSuccess", httpSession.getAttribute("signupSuccess"));
        return "login";
    }

}
