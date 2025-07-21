package com.me.book_management.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth/")
public class SignInController {

    @GetMapping("signin")
    public String login() {
        return "signin-form";
    }

    @PostMapping("signin")
    public String loginPost() {

        return "redirect:/home";
    }
}
