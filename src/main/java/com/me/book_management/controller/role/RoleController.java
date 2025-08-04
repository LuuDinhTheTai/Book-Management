package com.me.book_management.controller.role;

import com.me.book_management.annotation.hasPermission;
import com.me.book_management.constant.Constants;
import com.me.book_management.dto.request.role.CreateRoleRequest;
import com.me.book_management.dto.request.role.UpdateRoleRequest;
import com.me.book_management.exception.InputException;
import com.me.book_management.service.PermissionService;
import com.me.book_management.service.ResourceService;
import com.me.book_management.service.ActionService;
import com.me.book_management.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/roles/")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final PermissionService permissionService;
    private final ResourceService resourceService;
    private final ActionService actionService;

    @GetMapping("create")
    @hasPermission(permission = Constants.PERMISSION.CREATE_ROLE)
    public String create(Model model) {
        model.addAttribute("createRoleRequest", new CreateRoleRequest());
        model.addAttribute("resources", resourceService.list());
        model.addAttribute("actions", actionService.list());
        return "role/create-form";
    }

    @PostMapping("create")
    @hasPermission(permission = Constants.PERMISSION.CREATE_ROLE)
    public String create(@Valid @ModelAttribute("createRoleRequest") CreateRoleRequest request,
                         RedirectAttributes redirectAttributes) {
        try {
            roleService.create(request);
            redirectAttributes.addAttribute("successMessage", "Create role successfully!");
            return "redirect:/roles/list";

        } catch (Exception e) {
            redirectAttributes.addAttribute("errorMessage", "Create role failed!");
            return "redirect:/roles/list";
        }
    }

    @GetMapping("list")
    @hasPermission(permission = Constants.PERMISSION.READ_ROLE)
    public String list(Model model) {
        try {
            model.addAttribute("roles", roleService.list());
            model.addAttribute("totalPermissions", permissionService.findAll().size());
            return "role/list";
        } catch (Exception e) {

            model.addAttribute("errorMessage", "Failed to load roles");
            return "role/list";
        }
    }

    @GetMapping("update/{id}")
    @hasPermission(permission = Constants.PERMISSION.UPDATE_ROLE)
    public String update(@PathVariable Long id,
                         Model model) {
        model.addAttribute("role", roleService.find(id));
        model.addAttribute("resources", resourceService.list());
        model.addAttribute("actions", actionService.list());
        model.addAttribute("updateRoleRequest", new UpdateRoleRequest());
        return "role/update-form";
    }

    // Helper method to check if role has permission
    private boolean hasPermission(com.me.book_management.entity.rbac0.Role role, 
                                com.me.book_management.entity.rbac0.Resource resource, 
                                com.me.book_management.entity.rbac0.Action action) {
        return role.getPermissions().stream()
                .anyMatch(permission -> 
                    permission.getResource().getId().equals(resource.getId()) && 
                    permission.getAction().getId().equals(action.getId()));
    }

    @PostMapping("update/{id}")
    @hasPermission(permission = Constants.PERMISSION.UPDATE_ROLE)
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("updateRoleRequest") UpdateRoleRequest request,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return "role/update-form";
        }

        try {
            roleService.update(id, request);
            redirectAttributes.addAttribute("successMessage", "Update role successfully!");
            return "redirect:/roles/list";

        } catch (InputException e) {
            redirectAttributes.addAttribute("errorMessage", e.getMessage());
            return "redirect:roles/" + id;
        }
    }

    @PostMapping("delete/{id}")
    @hasPermission(permission = Constants.PERMISSION.DELETE_ROLE)
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirectAttributes) {
        roleService.delete(id);
        redirectAttributes.addAttribute("successMessage", "Delete role successfully!");
        return "redirect:/roles/list";
    }
}
