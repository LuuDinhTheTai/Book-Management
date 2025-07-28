package com.me.book_management.annotation.cart;

import com.me.book_management.constant.Constants;
import com.me.book_management.entity.account.Account;
import com.me.book_management.service.AccountService;
import com.me.book_management.util.CommonUtil;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Update.UpdateCartValidator.class)
public @interface Update {

    String message() default "Invalid update cart request";
    Class<?>[] groups() default {};
    Class<? extends jakarta.validation.Payload>[] payload() default {};

    @Component
    class UpdateCartValidator implements ConstraintValidator<Update, Object> {

        @Autowired
        private AccountService accountService;
        private Account account;

        @Override
        public void initialize(Update constraintAnnotation) {
            jakarta.validation.ConstraintValidator.super.initialize(constraintAnnotation);
            this.account = accountService.findByUsername(CommonUtil.getCurrentAccount());
        }

        @Override
        public boolean isValid(Object object, ConstraintValidatorContext context) {
            if (!CommonUtil.hasPermission(this.account, Constants.PERMISSION.UPDATE_CART)) {
                return false;
            }
            return true;
        }
    }
}
