package com.me.book_management.controller.cart;

import com.me.book_management.annotation.cart.Create;
import com.me.book_management.annotation.cart.Delete;
import com.me.book_management.annotation.cart.Update;
import com.me.book_management.dto.request.cart.UpdateCartRequest;
import com.me.book_management.entity.cart.Cart;
import com.me.book_management.exception.InputException;
import com.me.book_management.repository.cart.CartBookRepository;
import com.me.book_management.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/carts/")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartBookRepository cartBookRepository;

    @PostMapping("add/{id}")
    public String add(@Valid
                      @PathVariable("id")
                      Long bookId,
                      RedirectAttributes redirectAttributes) {
        try {
            cartService.add(bookId);
            redirectAttributes.addFlashAttribute("successMessage", "Book added to cart successfully!");

        } catch (InputException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/carts/list";
    }

    @GetMapping("list")
    public String list(Model model) {
        Cart cart = cartService.get();
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", cart.getTotalPrice());
        return "cart/list";
    }

    @PostMapping("update/{id}")
    public String update(@Valid
                         @PathVariable("id") Long cartBookId,
                         @ModelAttribute UpdateCartRequest request,
                         RedirectAttributes redirectAttributes) {
        try {
            cartService.update(cartBookId, request.getQty());
            
            redirectAttributes.addFlashAttribute("successMessage", "Cart updated successfully!");

        } catch (InputException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/carts/list";
    }

    @PostMapping("delete/{id}")
    public String delete(@Valid
                         @PathVariable("id") Long cartBookId,
                         RedirectAttributes redirectAttributes) {
        try {
            cartService.delete(cartBookId);
            redirectAttributes.addFlashAttribute("successMessage", "Item removed from cart!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to remove item from cart");
        }
        return "redirect:/carts/list";
    }
}
