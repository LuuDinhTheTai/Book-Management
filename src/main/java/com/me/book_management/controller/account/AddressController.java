package com.me.book_management.controller.account;

import com.me.book_management.dto.request.account.address.CreateAddressRequest;
import com.me.book_management.dto.request.account.address.UpdateAddressRequest;
import com.me.book_management.entity.account.Address;
import com.me.book_management.service.AccountService;
import com.me.book_management.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/addresses/")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final AccountService accountService;

    @GetMapping("create")
    public String createForm(Model model) {
        model.addAttribute("address", new CreateAddressRequest());
        return "address/create-form";
    }

    @PostMapping("create")
    public String create(@Valid @ModelAttribute("address") CreateAddressRequest request,
                        BindingResult bindingResult,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "address/create-form";
        }

        try {
            addressService.create(request);

            redirectAttributes.addFlashAttribute("successMessage", "Address created successfully!");
            return "redirect:/addresses/list";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to create address: " + e.getMessage());
            return "address/create-form";
        }
    }

    @GetMapping("list")
    public String list(Model model) {
        try {
            List<Address> addresses = addressService.list();
            model.addAttribute("addresses", addresses);
            return "address/list";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to load addresses: " + e.getMessage());
            return "address/list";
        }
    }

    @GetMapping("update/{id}")

    public String updateForm(@PathVariable Long id, Model model) {
        try {
            Address address = addressService.find(id);
            model.addAttribute("address", address);
            return "address/update-form";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Address not found: " + e.getMessage());
            return "redirect:/addresses/list";
        }
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable Long id,
                        @Valid @ModelAttribute("address") UpdateAddressRequest request,
                        BindingResult bindingResult,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "address/update-form";
        }

        try {
            addressService.update(id, request);
            redirectAttributes.addFlashAttribute("successMessage", "Address updated successfully!");
            return "redirect:/addresses/list";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to update address: " + e.getMessage());
            return "address/update-form";
        }
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            addressService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Address deleted successfully!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete address: " + e.getMessage());
        }

        return "redirect:/addresses/list";
    }
} 