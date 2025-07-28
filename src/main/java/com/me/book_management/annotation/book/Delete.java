package com.me.book_management.annotation.book;

import com.me.book_management.constant.Constants;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.book.Book;
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

    String message() default "Invalid delete book request";
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
            this.account = accountService.findByUsername(CommonUtil.getCurrentAccount());
        }

        @Override
        public boolean isValid(Long value, ConstraintValidatorContext context) {
            if (!CommonUtil.hasPermission(this.account, Constants.PERMISSION.DELETE_BOOK)) {
                return false;
            }
            if (!isOwner(value)) {
                return false;
            }
            return true;
        }

        private boolean isOwner(Long bookId) {
            Book book = bookService.find(bookId);
            return book != null && book.getAccount().getUsername().equals(this.account.getUsername());
        }
    }
}
