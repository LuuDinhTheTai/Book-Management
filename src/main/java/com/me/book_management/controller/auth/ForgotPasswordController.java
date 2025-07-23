package com.me.book_management.controller.auth;

import com.me.book_management.dto.request.ForgotPasswordRequest;
import com.me.book_management.exception.CustomException;
import com.me.book_management.exception.InputException;
import com.me.book_management.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final AuthService authService;

    @GetMapping("forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("forgotPasswordRequest", new ForgotPasswordRequest());
        return "auth/forgot-password-form";
    }

    @PostMapping("forgot-password")
    public String forgotPasswordPost(@Valid @ModelAttribute("forgotPasswordRequest") ForgotPasswordRequest request,
                                     Model model) {
        try {
            request.validate();
            authService.resetPassword(request);
            return "redirect:/auth/signin";

        } catch (InputException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/forgot-password-form";
        }
    }
}
