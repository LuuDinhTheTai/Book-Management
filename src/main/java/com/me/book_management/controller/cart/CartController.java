package com.me.book_management.controller.cart;

import com.me.book_management.entity.cart.Cart;
import com.me.book_management.exception.InputException;
import com.me.book_management.service.CartService;
import com.me.book_management.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/carts/")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("add/{id}")
    public String add(@PathVariable("id") Long bookId,
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
        model.addAttribute("carts", cartService.list());
        model.addAttribute("totalPrice", cartService.getTotalPrice());
        return "cart/list";
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Long id,
                         Cart cart,
                         RedirectAttributes redirectAttributes) {
        try {
            cartService.update(id, cart);
            redirectAttributes.addFlashAttribute("successMessage", "Cart updated successfully!");

        } catch (InputException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/carts/list";
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id,
                         RedirectAttributes redirectAttributes) {
        cartService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Item removed from cart!");
        return "redirect:/carts/list";
    }
}
