package com.me.book_management.controller.account;

import com.me.book_management.dto.request.account.UpdateAccountRequest;
import com.me.book_management.entity.account.Account;
import com.me.book_management.service.AccountService;
import com.me.book_management.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/accounts/")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("profile")
    public String profile(Model model) {
        String username = SecurityUtil.getCurrentAccount();
        model.addAttribute("account", accountService.findByUsername(username));
        return "account/profile";
    }

    @GetMapping("update/{id}")
    public String update(@PathVariable Long id,
                         Model model) {
        model.addAttribute("account", accountService.find(id));
        return "account/update-form";
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable Long id,
                         @Valid
                         @ModelAttribute("account")
                         UpdateAccountRequest request,
                         Model model) {
        accountService.update(id, request);
        return "redirect:/accounts/profile";
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        accountService.delete(id);
        return "redirect:/auth/signup";
    }
}
