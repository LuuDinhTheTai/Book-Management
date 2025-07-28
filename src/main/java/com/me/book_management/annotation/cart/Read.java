package com.me.book_management.annotation.cart;

import com.me.book_management.constant.Constants;
import com.me.book_management.entity.account.Account;
import com.me.book_management.service.AccountService;
import com.me.book_management.util.CommonUtil;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.Payload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Read.CreateCartValidator.class)
public @interface Read {

    String message() default "Invalid read cart request";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Component
    class CreateCartValidator implements jakarta.validation.ConstraintValidator<Read, Object> {

        @Autowired
        private AccountService accountService;
        private Account account;

        @Override
        public void initialize(Read constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
            this.account = accountService.findByUsername(CommonUtil.getCurrentAccount());
        }

        @Override
        public boolean isValid(Object object, jakarta.validation.ConstraintValidatorContext context) {
            if (!CommonUtil.hasPermission(this.account, Constants.PERMISSION.READ_CART)) {
                return false;
            }
            return true;
        }
    }
}
