package com.me.book_management.controller.account;

import com.me.book_management.annotation.hasPermission;
import com.me.book_management.annotation.resourceOwner;
import com.me.book_management.constant.Constants;
import com.me.book_management.dto.request.account.ProfileRequest;
import com.me.book_management.dto.request.account.UpdateAccountRequest;
import com.me.book_management.dto.request.book.ListBookRequest;
import com.me.book_management.dto.request.cart.ListCartRequest;
import com.me.book_management.dto.request.comment.ListCommentRequest;
import com.me.book_management.service.AccountService;
import com.me.book_management.service.BookService;
import com.me.book_management.service.CartService;
import com.me.book_management.service.CommentService;
import com.me.book_management.util.CommonUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/accounts/")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final BookService bookService;
    private final CartService cartService;
    private final CommentService commentService;

    @GetMapping("profile")
    @hasPermission(permission = Constants.PERMISSION.READ_ACCOUNT)
    public String profile(@ModelAttribute ProfileRequest request,
                          Model model) {
        String username = CommonUtil.getCurrentAccount();
        model.addAttribute("account", accountService.findByUsername(username));
        model.addAttribute("myBooks", bookService.list(
                ListBookRequest.builder()
                        .myBook(true)
                        .page(request.getBookPage())
                        .size(request.getBookSize())
                        .build())
        );
        model.addAttribute("carts", cartService.list(
                ListCartRequest.builder()
                        .page(request.getCartPage())
                        .size(request.getCartSize())
                        .build())
        );
        model.addAttribute("myComments", commentService.findByAccount(
                ListCommentRequest.builder()
                        .page(request.getCommentPage())
                        .size(request.getCommentSize())
                        .build())
        );
        model.addAttribute("activeTab", request.getTab());
        return "account/profile";
    }

    @GetMapping("update/{id}")
    @hasPermission(permission = Constants.PERMISSION.UPDATE_ACCOUNT)
    public String update(@resourceOwner(instance = Constants.CLASSNAME.ACCOUNT) @PathVariable Long id,
                         Model model) {
        model.addAttribute("account", accountService.find(id));
        return "account/update-form";
    }

    @PostMapping("update/{id}")
    @hasPermission(permission = Constants.PERMISSION.UPDATE_ACCOUNT)
    public String update(@resourceOwner(instance = Constants.CLASSNAME.ACCOUNT) @PathVariable Long id,
                         @Valid @ModelAttribute("account") UpdateAccountRequest request,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return "account/update-form";
        }
        try {
            request.validate();
            accountService.update(id, request);
            return "redirect:/accounts/profile";

        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "account/update-form";
        }
    }

    @PostMapping("delete/{id}")
    @hasPermission(permission = Constants.PERMISSION.DELETE_ACCOUNT)
    public String delete(@resourceOwner(instance = Constants.CLASSNAME.ACCOUNT) @PathVariable Long id) {
        accountService.delete(id);
        return "redirect:/auth/signup";
    }
}
