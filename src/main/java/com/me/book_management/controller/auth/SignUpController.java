package com.me.book_management.controller.auth;

import com.me.book_management.dto.request.auth.SignUpRequest;
import com.me.book_management.exception.InputException;
import com.me.book_management.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class SignUpController {

    private final AuthService authService;

    @GetMapping("signup")
    public String signUp(Model model) {
        model.addAttribute("signUpRequest", new SignUpRequest());
        return "auth/signup-form";
    }

    @PostMapping("signup")
    public String signUp(@Valid
                         @ModelAttribute("signUpRequest")
                         SignUpRequest request,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/signup-form";
        }
        try {
            request.validate();
            authService.signUp(request);
            return "redirect:/auth/signin";

        } catch (InputException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/signup-form";
        }
    }
}
