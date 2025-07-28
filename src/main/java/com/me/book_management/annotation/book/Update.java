package com.me.book_management.annotation.book;

import com.me.book_management.constant.Constants;
import com.me.book_management.dto.request.book.UpdateBookRequest;
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
@Constraint(validatedBy = Update.UpdateBookValidator.class)
public @interface Update {

    String message() default "Invalid update book request";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Component
    class UpdateBookValidator implements ConstraintValidator<Update, UpdateBookRequest> {

        @Autowired
        private AccountService accountService;
        @Autowired
        private BookService bookService;
        private Account account;

        @Override
        public void initialize(Update constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
            this.account = accountService.findByUsername(CommonUtil.getCurrentAccount());
        }

        @Override
        public boolean isValid(UpdateBookRequest request, ConstraintValidatorContext constraintValidatorContext) {
            if (CommonUtil.isNull(request)) {
                return false;
            }
            Book book = bookService.find(request.getId());
            if (CommonUtil.isNull(book)) {
                return false;
            }
            if (!isOwner(this.account, book)) {
                return false;
            }
            if (CommonUtil.hasPermission(this.account, Constants.PERMISSION.UPDATE_BOOK)) {
                return true;
            }
            return true;
        }

        private boolean isOwner(Account account, Book book) {
            return book.getAccount().getUsername().equals(account.getUsername());
        }
    }
}
