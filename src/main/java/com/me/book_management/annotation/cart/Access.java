package com.me.book_management.annotation.cart;

import com.me.book_management.constant.Constants;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.cart.Cart;
import com.me.book_management.exception.UnauthorizedAccessException;
import com.me.book_management.service.AccountService;
import com.me.book_management.service.CartService;
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
@Constraint(validatedBy = Access.AccessCartValidator.class)
public @interface Access {

    String message() default "Không có quyền truy cập giỏ hàng";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Component
    class AccessCartValidator implements ConstraintValidator<Access, Long> {

        @Autowired
        private AccountService accountService;
        @Autowired
        private CartService cartService;

        @Override
        public void initialize(Access constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }

        @Override
        public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
            try {
                Account account = accountService.findByUsername(CommonUtil.getCurrentAccount());
                if (CommonUtil.isNull(account)) {
                    throw new UnauthorizedAccessException("Người dùng không tồn tại");
                }

                Cart cart = cartService.find(id);
                if (CommonUtil.isNull(cart)) {
                    throw new UnauthorizedAccessException("Giỏ hàng không tồn tại");
                }

                if (!isOwner(account, cart)) {
                    throw new UnauthorizedAccessException("Bạn không có quyền truy cập vào giỏ hàng này. Chỉ chủ sở hữu mới có thể truy cập.");
                }
                
                return true;

            } catch (Exception e) {
                if (e instanceof UnauthorizedAccessException) {
                    throw e;
                }
                throw new UnauthorizedAccessException("Lỗi kiểm tra quyền truy cập giỏ hàng: " + e.getMessage());
            }
        }

        private boolean isOwner(Account account, Cart cart) {
            return cart.getAccount().getUsername().equals(account.getUsername());
        }
    }
} 