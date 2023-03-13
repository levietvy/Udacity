package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    CredentialService credentialService;
    AuthenticationService authenticationService;

    public CredentialController(CredentialService credentialService, AuthenticationService authenticationService) {
        this.credentialService = credentialService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/add")
    public String addCredential(@ModelAttribute("credential") Credential credential, HttpSession httpSession) {
        credential.setUserId(authenticationService.getUserId());
        int isSuccess = 0;
        try {
            isSuccess = credentialService.insertCredential(credential);
            if (isSuccess > 0) {
                assignHttpSession(httpSession, isSuccess, null, null);
            } else {
                assignHttpSession(httpSession, isSuccess, "Can't add new credential. Please try again!", null);
            }
        } catch (Exception ex) {
            assignHttpSession(httpSession, isSuccess, null, ex.getMessage());
        }
        return "redirect:/result";
    }

    private static HttpSession assignHttpSession(HttpSession httpSession, int isSuccess, String errorMessage, String exceptionMessage){
        httpSession.setAttribute("isSuccess", isSuccess);
        httpSession.setAttribute("errorMessage", errorMessage);
        httpSession.setAttribute("exceptionMessage", exceptionMessage);
        return httpSession;
    }
}
