package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    private UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signupPage(){
        return "signup";
    }

    @PostMapping
    public String signupUser(@ModelAttribute User user, Model model, HttpSession httpSession){
        String signupError = "";

        if (userService.getUser(user.getUsername()) != null){
            signupError = "Username is already exist!";
        } else {
            int addedFlg = userService.createUser(user);
            if (addedFlg <= 0) {
                signupError = "Signup error. Please try again!";
            }
        }

        if ("".equals(signupError)){
            httpSession.setAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", signupError);
            return "signup";
        }
        return "redirect:/login";
    }
}
