package com.me.book_management.controller.book;

import com.me.book_management.dto.request.book.CreateCategoryRequest;
import com.me.book_management.dto.request.book.UpdateCategoryRequest;
import com.me.book_management.entity.book.Category;
import com.me.book_management.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories/")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("create")
    public String create(Model model) {
        try {
            model.addAttribute("createCategoryRequest", new CreateCategoryRequest());
            return "category/create-form";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "category/create-form";
        }
    }

    @PostMapping("create")
    public String create(@Valid @ModelAttribute("createCategoryRequest") CreateCategoryRequest request,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return "category/create-form";
        }
        
        try {
            Category category = categoryService.create(request);
            redirectAttributes.addFlashAttribute("successMessage", "Category created successfully!");
            return "redirect:/categories/" + category.getId();
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "category/create-form";
        }
    }

    @GetMapping("list")
    public String list(Model model) {
        try {
            model.addAttribute("categories", categoryService.list());
            return "category/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to load categories: " + e.getMessage());
            return "category/list";
        }
    }

    @GetMapping("{id}")
    public String find(@PathVariable Long id, Model model) {
        try {
            Category category = categoryService.find(id);
            model.addAttribute("category", category);
            model.addAttribute("books", categoryService.findBookByCategory(id));
            return "category/detail";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Category not found: " + e.getMessage());
            return "category/detail";
        }
    }

    @GetMapping("update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        try {
            Category category = categoryService.find(id);
            UpdateCategoryRequest request = new UpdateCategoryRequest();
            request.setId(category.getId());
            request.setName(category.getName());
            request.setDescription(category.getDescription());
            model.addAttribute("updateCategoryRequest", request);
            return "category/update-form";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Category not found: " + e.getMessage());
            return "category/update-form";
        }
    }

    @PostMapping("update")
    public String update(@Valid @ModelAttribute("updateCategoryRequest") UpdateCategoryRequest request,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return "category/update-form";
        }
        
        try {
            Category category = categoryService.update(request.getId(), request);
            redirectAttributes.addFlashAttribute("successMessage", "Category updated successfully!");
            return "redirect:/categories/" + category.getId();
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "category/update-form";
        }
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Category deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete category: " + e.getMessage());
        }
        return "redirect:/categories/list";
    }
}
