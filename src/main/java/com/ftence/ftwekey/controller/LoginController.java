package com.ftence.ftwekey.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LoginController {
    @GetMapping("/oauth2/authorization")
    public String login() {
        return "redirect:/oauth2/authorization/intra42";
    }

}
