package com.me.book_management.annotation.comment;

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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Delete.DeleteCommentValidator.class)
public @interface Delete {

    String message() default "Invalid delete comment request";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Component
    class DeleteCommentValidator implements ConstraintValidator<Delete, Long> {

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
        public boolean isValid(Long commentId, ConstraintValidatorContext context) {
            if (!CommonUtil.hasPermission(this.account, Constants.PERMISSION.DELETE_COMMENT)) {
                return false;
            }
            if (!isOwner(commentId)) {
                return false;
            }
            return true;
        }

        private boolean isOwner(Long bookId) {
            return false;
        }
    }
}
