package com.me.book_management.controller.cart;

import com.me.book_management.dto.request.cart.AddItemRequest;
import com.me.book_management.dto.request.cart.DecreaseItemRequest;
import com.me.book_management.dto.request.cart.IncreaseItemRequest;
import com.me.book_management.entity.cart.Cart;
import com.me.book_management.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/carts/")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("create")
    public String create(RedirectAttributes redirectAttributes) {
        try {
            Cart cart = cartService.create();
            redirectAttributes.addFlashAttribute("successMessage", "Cart created successfully!");
            return "redirect:/carts/list";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create cart: " + e.getMessage());
            return "redirect:/carts/list";
        }
    }

    @GetMapping("{id}")
    public String find(@Valid @PathVariable Long id, Model model) {
        try {
            Cart cart = cartService.find(id);
            model.addAttribute("cart", cart);
            model.addAttribute("totalPrice", cart.getTotalPrice());
            return "cart/detail";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Cart not found: " + e.getMessage());
            return "cart/detail";
        }
    }

    @GetMapping("list")
    public String list(Model model) {
        try {
            List<Cart> carts = cartService.list();
            model.addAttribute("carts", carts);

            return "cart/list";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to load cart: " + e.getMessage());
            return "cart/list";
        }
    }

    @PostMapping("delete/{id}")
    public String delete(@Valid @PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            cartService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Cart deleted successfully!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete cart: " + e.getMessage());
        }
        return "redirect:/carts/list";
    }

    @PostMapping("add-item")
    public String addItem(@ModelAttribute AddItemRequest request, RedirectAttributes redirectAttributes) {
        try {
            Cart cart = cartService.addItem(request);
            redirectAttributes.addFlashAttribute("successMessage", "Item added to cart successfully!");
            return "redirect:/carts/list";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add item: " + e.getMessage());
            return "redirect:/carts/list";
        }
    }

    @PostMapping("increase-item/{id}")
    public String increaseItem(@PathVariable
                               Long id,
                               @Valid
                               @ModelAttribute
                               IncreaseItemRequest request,
                               RedirectAttributes redirectAttributes) {
        try {
            Cart cart = cartService.increaseItem(id, request);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm số lượng thành công!");

            return "redirect:/carts/list";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add item: " + e.getMessage());
            return "redirect:/carts/list";
        }
    }

    @PostMapping("decrease-item/{id}")
    public String decreaseItem(@PathVariable
                               Long id,
                               @Valid
                               @ModelAttribute
                               DecreaseItemRequest request,
                               RedirectAttributes redirectAttributes) {
        try {
            Cart cart = cartService.decreaseItem(id, request);
            redirectAttributes.addFlashAttribute("successMessage", "Giảm số lượng thành công!");

            return "redirect:/carts/list";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to decrease item: " + e.getMessage());
            return "redirect:/carts/list";
        }
    }
}
