package com.me.book_management.annotation.book;

import com.me.book_management.constant.Constants;
import com.me.book_management.dto.request.book.UpdateBookRequest;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.book.Book;
import com.me.book_management.exception.UnauthorizedAccessException;
import com.me.book_management.service.AccountService;
import com.me.book_management.service.BookService;
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
@Constraint(validatedBy = Update.UpdateBookValidator.class)
public @interface Update {

    String message() default "Yêu cầu cập nhật sách không hợp lệ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Component
    class UpdateBookValidator implements ConstraintValidator<Update, Long> {

        @Autowired
        private AccountService accountService;
        @Autowired
        private BookService bookService;

        @Override
        public void initialize(Update constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }

        @Override
        public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
            try {
                Account account = accountService.findByUsername(CommonUtil.getCurrentAccount());
                if (CommonUtil.isNull(account)) {
                    throw new UnauthorizedAccessException("Người dùng không tồn tại");
                }

                Book book = bookService.find(id);
                if (CommonUtil.isNull(book)) {
                    throw new UnauthorizedAccessException("Sách không tồn tại");
                }

                if (!isOwner(account, book)) {
                    throw new UnauthorizedAccessException("Bạn không có quyền sửa sách này. Chỉ chủ sở hữu mới có thể sửa sách.");
                }

                if (!CommonUtil.hasPermission(account, Constants.PERMISSION.UPDATE_BOOK)) {
                    throw new UnauthorizedAccessException("Bạn không có quyền sửa sách");
                }
                
                return true;
            } catch (Exception e) {
                if (e instanceof UnauthorizedAccessException) {
                    throw e;
                }
                throw new UnauthorizedAccessException("Lỗi kiểm tra quyền sửa sách: " + e.getMessage());
            }
        }

        private boolean isOwner(Account account, Book book) {
            return book.getAccount().getUsername().equals(account.getUsername());
        }
    }
}
