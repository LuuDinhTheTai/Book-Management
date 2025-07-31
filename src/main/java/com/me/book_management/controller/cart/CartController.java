package com.me.book_management.controller.cart;

import com.me.book_management.dto.request.cart.AddItemRequest;
import com.me.book_management.dto.request.cart.UpdateItemRequest;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.cart.Cart;
import com.me.book_management.entity.cart.CartBook;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.repository.cart.CartBookRepository;
import com.me.book_management.repository.cart.CartRepository;
import com.me.book_management.service.CartService;
import com.me.book_management.util.SecurityUtil;
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
    private final CartBookRepository cartBookRepository;
    private final CartRepository cartRepository;
    private final AccountRepository accountRepository;

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
    public String find(@PathVariable Long id, Model model) {
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
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
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
            // If cartId is not provided, get the user's active cart
            if (request.getCartId() == null) {
                String currentUsername = SecurityUtil.getCurrentAccount();
                Account account = accountRepository.findByUsername(currentUsername)
                        .orElseThrow(() -> new RuntimeException("Account not found"));
                
                Cart activeCart = cartRepository.findFirstByAccountOrderByIdDesc(account)
                        .filter(c -> "ACTIVE".equals(c.getStatus()))
                        .orElseGet(() -> {
                            Cart newCart = new Cart();
                            newCart.setAccount(account);
                            newCart.setStatus("ACTIVE");
                            newCart.setTotalPrice(0.0f);
                            return cartRepository.save(newCart);
                        });
                
                request.setCartId(activeCart.getId());
            }
            
            Cart cart = cartService.addItem(request);
            redirectAttributes.addFlashAttribute("successMessage", "Item added to cart successfully!");
            return "redirect:/carts/" + cart.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add item: " + e.getMessage());
            return "redirect:/carts/list";
        }
    }

    @PostMapping("update-item")
    public String updateItem(@ModelAttribute UpdateItemRequest request, RedirectAttributes redirectAttributes) {
        try {
            Cart cart = cartService.updateItem(request);
            redirectAttributes.addFlashAttribute("successMessage", "Cart updated successfully!");
            return "redirect:/carts/" + cart.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update cart: " + e.getMessage());
            return "redirect:/carts/list";
        }
    }

    @PostMapping("update/{id}")
    public String updateItemQuantity(@PathVariable Long id, @RequestParam Integer qty, RedirectAttributes redirectAttributes) {
        try {
            UpdateItemRequest request = new UpdateItemRequest();
            request.setCartBookId(id);
            request.setQty(qty);
            Cart cart = cartService.updateItem(request);
            redirectAttributes.addFlashAttribute("successMessage", "Quantity updated successfully!");
            return "redirect:/carts/" + cart.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update quantity: " + e.getMessage());
            return "redirect:/carts/list";
        }
    }

    @PostMapping("delete-item/{id}")
    public String deleteItem(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            CartBook cartBook = cartBookRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Cart item not found"));
            Long cartId = cartBook.getCart().getId();
            cartBookRepository.delete(cartBook);
            redirectAttributes.addFlashAttribute("successMessage", "Item removed from cart successfully!");
            return "redirect:/carts/" + cartId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to remove item: " + e.getMessage());
            return "redirect:/carts/list";
        }
    }

    @PostMapping("clear")
    public String clearCart(RedirectAttributes redirectAttributes) {
        try {
            String currentUsername = SecurityUtil.getCurrentAccount();
            Account account = accountRepository.findByUsername(currentUsername)
                    .orElseThrow(() -> new RuntimeException("Account not found"));
            
            Cart cart = cartRepository.findFirstByAccountOrderByIdDesc(account)
                    .orElseThrow(() -> new RuntimeException("Cart not found"));
            
            cartBookRepository.deleteByCart(cart);
            cart.setTotalPrice(0.0f);
            cartRepository.save(cart);
            
            redirectAttributes.addFlashAttribute("successMessage", "Cart cleared successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to clear cart: " + e.getMessage());
        }
        return "redirect:/carts/list";
    }
}
