package com.me.book_management.annotation.book;

import com.me.book_management.constant.Constants;
import com.me.book_management.dto.request.book.UpdateBookRequest;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.book.Book;
import com.me.book_management.entity.rbac0.Permission;
import com.me.book_management.entity.rbac0.Role;
import com.me.book_management.service.AccountService;
import com.me.book_management.service.BookService;
import com.me.book_management.util.CommonUtil;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UpdateRequest.UpdateValidator.class)
public @interface UpdateRequest {

    String message() default "Invalid update request";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class UpdateValidator implements ConstraintValidator<UpdateRequest, UpdateBookRequest> {

        @Autowired
        private AccountService accountService;
        @Autowired
        private BookService bookService;
        private Account account;
        private boolean isAdmin = false;
        private boolean isUser = false;
        private boolean hasAuthorization = false;

        @Override
        public void initialize(UpdateRequest constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
            this.account = accountService.findByUsername(CommonUtil.getCurrentAccount());

            if (account != null) {
                for (Role role : account.getRoles()) {
                    if (role.getName().equals(Constants.ROLE.ADMIN)) {
                        isAdmin = true;
                        break;
                    } else if (role.getName().equals(Constants.ROLE.USER)) {
                        isUser = true;
                        for (Permission permission : role.getPermissions()) {
                            if (permission.getName().equals(Constants.PERMISSION.UPDATE_BOOK)) {
                                hasAuthorization = true;
                                break;
                            }
                        }
                    }
                }
            }
        }

        @Override
        public boolean isValid(UpdateBookRequest request, ConstraintValidatorContext constraintValidatorContext) {
            if (isAdmin == true) {
                return true;
            }
            if (isUser == false) {
                return false;
            }
            if (hasAuthorization == false) {
                return false;
            }
            if (CommonUtil.isNull(request)) {
                return false;
            }
            Book book = bookService.find(request.getId());
            if (CommonUtil.isNull(book)) {
                return false;
            }
            if (!book.getAccount().getUsername().equals(this.account.getUsername())) {
                return false;
            }
            return true;
        }
    }
}
