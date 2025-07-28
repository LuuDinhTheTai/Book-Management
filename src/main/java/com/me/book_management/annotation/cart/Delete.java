package com.me.book_management.annotation.cart;

import com.me.book_management.constant.Constants;
import com.me.book_management.entity.account.Account;
import com.me.book_management.service.AccountService;
import com.me.book_management.service.BookService;
import com.me.book_management.util.CommonUtil;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Delete.DeleteCartValidator.class)
public @interface Delete {

    String message() default "Invalid delete book request";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Component
    class DeleteCartValidator implements ConstraintValidator<Delete, Object> {

        @Autowired
        private AccountService accountService;
        @Autowired
        private BookService bookService;
        private Account account;

        @Override
        public void initialize(Delete constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
            this.account = accountService.findByUsername(CommonUtil.getCurrentAccount());
        }

        @Override
        public boolean isValid(Object object, ConstraintValidatorContext context) {
            if (!CommonUtil.hasPermission(this.account, Constants.PERMISSION.DELETE_BOOK)) {
                return false;
            }
//            if (!isOwner(value)) {
//                return false;
//            }
            return true;
        }

        private boolean isOwner(Long bookId) {
            return true;
        }
    }
}
