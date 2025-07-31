package com.me.book_management.annotation.cart;

import com.me.book_management.constant.Constants;
import com.me.book_management.entity.account.Account;
import com.me.book_management.exception.UnauthorizedAccessException;
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
@Constraint(validatedBy = Create.CreateCartValidator.class)
public @interface Create {

    String message() default "Yêu cầu tạo giỏ hàng không hợp lệ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Component
    class CreateCartValidator implements ConstraintValidator<Create, Object> {

        @Autowired
        private AccountService accountService;

        @Override
        public void initialize(Create constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }

        @Override
        public boolean isValid(Object object, ConstraintValidatorContext context) {
            try {
                Account account = accountService.findByUsername(CommonUtil.getCurrentAccount());
                if (CommonUtil.isNull(account)) {
                    throw new UnauthorizedAccessException("Người dùng không tồn tại");
                }

                if (!CommonUtil.hasPermission(account, Constants.PERMISSION.CREATE_CART)) {
                    throw new UnauthorizedAccessException("Bạn không có quyền tạo giỏ hàng");
                }
                return true;

            } catch (Exception e) {
                if (e instanceof UnauthorizedAccessException) {
                    throw e;
                }
                throw new UnauthorizedAccessException("Lỗi kiểm tra quyền tạo giỏ hàng: " + e.getMessage());
            }
        }
    }
}

