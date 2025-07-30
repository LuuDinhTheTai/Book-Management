package com.me.book_management.annotation.book;

import com.me.book_management.constant.Constants;
import com.me.book_management.dto.request.book.CreateBookRequest;
import com.me.book_management.entity.account.Account;
import com.me.book_management.service.AccountService;
import com.me.book_management.util.CommonUtil;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Create.CreateBookValidator.class)
public @interface Create {

    String message() default "Invalid create book request";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Component
    class CreateBookValidator implements ConstraintValidator<Create, CreateBookRequest> {

        @Autowired
        private AccountService accountService;
        private Account account;

        @Override
        public void initialize(Create constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
            this.account = accountService.findByUsername(CommonUtil.getCurrentAccount());
        }

        @Override
        public boolean isValid(CreateBookRequest request, ConstraintValidatorContext context) {
            if (!CommonUtil.hasPermission(this.account, Constants.PERMISSION.CREATE_BOOK)) {
                return false;
            }
            return true;
        }
    }
}
