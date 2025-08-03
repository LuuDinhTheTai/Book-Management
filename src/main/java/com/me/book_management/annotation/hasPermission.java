package com.me.book_management.annotation;

import com.me.book_management.entity.account.Account;
import com.me.book_management.exception.ForbiddenException;
import com.me.book_management.exception.UnauthorizedException;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.util.CommonUtil;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = hasPermission.hasPermissionValidator.class)
public @interface hasPermission {

    String message() default "You are not authorized";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String permission();

    @Component
    @RequiredArgsConstructor
    class hasPermissionValidator implements ConstraintValidator<hasPermission, Object> {

        private final AccountRepository accountRepository;
        private String permission;

        @Override
        public void initialize(hasPermission constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
            this.permission = constraintAnnotation.permission();
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            Account currentAccount = accountRepository.findByUsername(CommonUtil.getCurrentAccount())
                    .orElseThrow(() -> new UnauthorizedException("You are not authorized"));

            if (!CommonUtil.hasPermission(currentAccount, permission)) {
                throw new ForbiddenException("You are not authorized");
            }

            return true;
        }
    }
}
