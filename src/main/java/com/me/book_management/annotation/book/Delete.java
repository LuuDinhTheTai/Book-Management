package com.me.book_management.annotation.book;

import com.me.book_management.constant.Constants;
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
@Constraint(validatedBy = Delete.DeleteBookValidator.class)
public @interface Delete {

    String message() default "Yêu cầu xóa sách không hợp lệ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Component
    class DeleteBookValidator implements ConstraintValidator<Delete, Long> {

        @Autowired
        private AccountService accountService;
        @Autowired
        private BookService bookService;
        private Account account;

        @Override
        public void initialize(Delete constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }

        @Override
        public boolean isValid(Long bookId, ConstraintValidatorContext context) {
            try {
                this.account = accountService.findByUsername(CommonUtil.getCurrentAccount());
                if (CommonUtil.isNull(this.account)) {
                    throw new UnauthorizedAccessException("Người dùng không tồn tại");
                }

                if (!CommonUtil.hasPermission(this.account, Constants.PERMISSION.DELETE_BOOK)) {
                    throw new UnauthorizedAccessException("Bạn không có quyền xóa sách");
                }

                if (!isOwner(bookId)) {
                    throw new UnauthorizedAccessException("Bạn không có quyền xóa sách này. Chỉ chủ sở hữu mới có thể xóa sách.");
                }
                
                return true;
            } catch (Exception e) {
                if (e instanceof UnauthorizedAccessException) {
                    throw e;
                }
                throw new UnauthorizedAccessException("Lỗi kiểm tra quyền xóa sách: " + e.getMessage());
            }
        }

        private boolean isOwner(Long bookId) {
            Book book = bookService.find(bookId);
            return book != null && book.getAccount().getUsername().equals(this.account.getUsername());
        }
    }
}
