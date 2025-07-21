package com.me.book_management.controller.auth;

import com.me.book_management.dto.request.SignUpRequest;
import com.me.book_management.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class SignUpController {

    private final AuthService authService;

    @GetMapping("signup")
    public String signUp() {
        return "signup-form";
    }

    @PostMapping("signup")
    public String signUp(@Valid @RequestBody SignUpRequest request,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", result.getAllErrors());
            return "signup-form";
        }
        request.validate();
        authService.signUp(request);
        return "redirect:/auth/signin";
    }
}
