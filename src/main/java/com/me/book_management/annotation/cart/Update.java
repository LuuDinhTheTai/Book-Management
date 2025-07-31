package com.me.book_management.annotation.cart;

import com.me.book_management.constant.Constants;
import com.me.book_management.dto.request.cart.UpdateItemRequest;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.cart.Cart;
import com.me.book_management.entity.cart.CartBook;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.exception.UnauthorizedAccessException;
import com.me.book_management.repository.cart.CartBookRepository;
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
@Constraint(validatedBy = Update.UpdateCartValidator.class)
public @interface Update {

    String message() default "Yêu cầu cập nhật giỏ hàng không hợp lệ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Component
    class UpdateCartValidator implements ConstraintValidator<Update, UpdateItemRequest> {

        @Autowired
        private AccountService accountService;
        @Autowired
        private CartBookRepository cartBookRepository;

        @Override
        public void initialize(Update constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }

        @Override
        public boolean isValid(UpdateItemRequest request, ConstraintValidatorContext constraintValidatorContext) {
            try {
                Account account = accountService.findByUsername(CommonUtil.getCurrentAccount());
                if (CommonUtil.isNull(account)) {
                    throw new UnauthorizedAccessException("Người dùng không tồn tại");
                }

                if (!CommonUtil.hasPermission(account, Constants.PERMISSION.UPDATE_CART)) {
                    throw new UnauthorizedAccessException("Bạn không có quyền cập nhật giỏ hàng");
                }

                CartBook cartBook = cartBookRepository.findById(request.getCartBookId())
                        .orElseThrow(() -> new NotFoundException("Không tìm thấy item"));
                Cart cart = cartBook.getCart();

                if (!isOwner(account, cart)) {
                    throw new UnauthorizedAccessException("Bạn không có quyền cập nhật giỏ hàng");
                }
                return true;

            } catch (Exception e) {
                if (e instanceof UnauthorizedAccessException) {
                    throw e;
                }
                throw new UnauthorizedAccessException("Lỗi kiểm tra quyền cập nhật giỏ hàng: " + e.getMessage());
            }
        }

        private boolean isOwner(Account account, Cart cart) {
            return cart.getAccount().getUsername().equals(account.getUsername());
        }
    }
}
