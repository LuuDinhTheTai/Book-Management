package com.me.book_management.controller.auth;

import com.me.book_management.constant.Constants;
import com.me.book_management.exception.InputException;
import com.me.book_management.util.CookieUtil;
import com.me.book_management.util.JwtUtil;
import com.me.book_management.dto.request.SignInRequest;
import com.me.book_management.exception.CustomException;
import com.me.book_management.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
public class SignInController {

    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final AuthService authService;

    @GetMapping("signin")
    public String login(Model model) {
        model.addAttribute("signInRequest", new SignInRequest());
        return "auth/signin-form";
    }

    @PostMapping("signin")
    public String loginPost(@Valid @ModelAttribute("signInRequest") SignInRequest request,
                            HttpServletResponse response,
                            Model model) {
        try {
            request.validate();

            String token = jwtUtil.generateToken(authService.signIn(request));
            Cookie tokenCookie = cookieUtil.create(Constants.COOKIE.ACCESS_TOKEN, token);
            response.addCookie(tokenCookie);

            return "redirect:/books/create";

        } catch (InputException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/signin-form";
        }
    }
}
