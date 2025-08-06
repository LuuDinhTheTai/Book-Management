package com.me.book_management.controller.cart;

import com.me.book_management.annotation.hasPermission;
import com.me.book_management.constant.Constants;
import com.me.book_management.dto.request.cart.ListOrdersRequest;
import com.me.book_management.service.OrdersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders/")
@RequiredArgsConstructor
public class OrderController {

    private final OrdersService ordersService;

    @GetMapping("list")
    @hasPermission(permission = Constants.PERMISSION.READ_CART)
    public String list(@Valid @ModelAttribute ListOrdersRequest request,
                       Model model) {
        model.addAttribute("orders", ordersService.list(request));
        return "order/list";
    }
}
