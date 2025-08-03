package com.me.book_management.annotation.book;

import com.me.book_management.constant.Constants;
import com.me.book_management.entity.account.Account;
import com.me.book_management.exception.ForbiddenException;
import com.me.book_management.exception.NotFoundException;
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
@Constraint(validatedBy = CreateBookPermission.CreateBookRequestValidator.class)
public @interface CreateBookPermission {

    String message() default "You are not authorized";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Component
    @RequiredArgsConstructor
    class CreateBookRequestValidator implements ConstraintValidator<CreateBookPermission, Object> {

        private final AccountRepository accountRepository;

        @Override
        public void initialize(CreateBookPermission constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }

        @Override
        public boolean isValid(Object object, ConstraintValidatorContext context) {
            Account currentAccount = accountRepository.findByUsername(CommonUtil.getCurrentAccount())
                    .orElseThrow(() -> new NotFoundException("Account not found"));

            if (CommonUtil.hasPermission(currentAccount, Constants.PERMISSION.CREATE_BOOK)) {
                throw new ForbiddenException("You are not authorized");
            }

            return true;
        }
    }
}
