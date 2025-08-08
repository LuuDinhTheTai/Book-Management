package com.me.book_management.controller.auth;

import com.me.book_management.constant.Constants;
import com.me.book_management.exception.InputException;
import com.me.book_management.service.AccountService;
import com.me.book_management.util.CookieUtil;
import com.me.book_management.util.JwtUtil;
import com.me.book_management.dto.request.auth.SignInRequest;
import com.me.book_management.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class SignInController {

    private final AuthService authService;

    @GetMapping("signin")
    public String login(Model model) {
        model.addAttribute("signInRequest", new SignInRequest());
        return "auth/signin-form";
    }

    @PostMapping("signin")
    public String login(@Valid
                        @ModelAttribute("signInRequest")
                        SignInRequest request,
                        BindingResult bindingResult,
                        HttpServletResponse response,
                        Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/signin-form";
        }

        try {
            request.validate();

            response.addCookie(authService.signIn(request));

            return "redirect:/books/list";

        } catch (InputException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/signin-form";
        }
    }
}
