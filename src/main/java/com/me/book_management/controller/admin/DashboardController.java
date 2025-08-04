package com.me.book_management.controller.admin;

import com.me.book_management.annotation.hasPermission;
import com.me.book_management.constant.Constants;
import com.me.book_management.service.AccountService;
import com.me.book_management.service.BookService;
import com.me.book_management.service.CartService;
import com.me.book_management.service.CategoryService;
import com.me.book_management.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/")
@RequiredArgsConstructor
@Slf4j
public class DashboardController {

    private final BookService bookService;
    private final AccountService accountService;
    private final CartService cartService;
    private final CategoryService categoryService;
    private final RoleService roleService;

    @GetMapping("dashboard")
    @hasPermission(permission = Constants.PERMISSION.READ_ACCOUNT)
    public String dashboard(Model model) {
        try {
            // Get statistics for the dashboard
            long totalBooks = getTotalBooks();
            long totalUsers = getTotalUsers();
            long totalOrders = getTotalOrders();
            String totalRevenue = calculateTotalRevenue();
            
            // Add statistics to model
            model.addAttribute("totalBooks", totalBooks);
            model.addAttribute("totalUsers", totalUsers);
            model.addAttribute("totalOrders", totalOrders);
            model.addAttribute("totalRevenue", totalRevenue);
            
            // Add additional data for quick actions
            model.addAttribute("categories", categoryService.list());
            model.addAttribute("roles", roleService.list());
            
            log.info("Dashboard loaded successfully - Books: {}, Users: {}, Orders: {}, Revenue: {}", 
                    totalBooks, totalUsers, totalOrders, totalRevenue);
            
        } catch (Exception e) {
            log.error("Error loading dashboard: {}", e.getMessage(), e);
            model.addAttribute("errorMessage", "Failed to load dashboard data. Please try again.");
            
            // Set default values
            model.addAttribute("totalBooks", 0);
            model.addAttribute("totalUsers", 0);
            model.addAttribute("totalOrders", 0);
            model.addAttribute("totalRevenue", "$0");
        }
        
        return "admin/dashboard";
    }

    /**
     * Get total number of books in the system
     */
    private long getTotalBooks() {
        try {
            // Create a simple request to get all books
            com.me.book_management.dto.request.book.ListBookRequest request = 
                new com.me.book_management.dto.request.book.ListBookRequest();
            request.setPage(0);
            request.setSize(Integer.MAX_VALUE); // Get all books
            
            return bookService.list(request).getTotalElements();
        } catch (Exception e) {
            log.error("Error getting total books: {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Get total number of users in the system
     */
    private long getTotalUsers() {
        try {
            return accountService.count();
        } catch (Exception e) {
            log.error("Error getting total users: {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Get total number of orders/carts in the system
     */
    private long getTotalOrders() {
        try {
            List<com.me.book_management.entity.cart.Cart> carts = cartService.list();
            return carts.size();
        } catch (Exception e) {
            log.error("Error getting total orders: {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Calculate total revenue from all orders
     */
    private String calculateTotalRevenue() {
        try {
            List<com.me.book_management.entity.cart.Cart> carts = cartService.list();
            
            double totalRevenue = carts.stream()
                .mapToDouble(cart -> {
                    // Calculate cart total based on items
                    return cart.getCartBooks().stream()
                        .mapToDouble(cartBook -> {
                            float price = cartBook.getBook().getPrice();
                            return price * cartBook.getQty();
                        })
                        .sum();
                })
                .sum();
            
            return String.format("$%.2f", totalRevenue);
        } catch (Exception e) {
            log.error("Error calculating total revenue: {}", e.getMessage());
            return "$0.00";
        }
    }
} 