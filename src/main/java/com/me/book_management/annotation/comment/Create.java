package com.me.book_management.annotation.comment;

import com.me.book_management.constant.Constants;
import com.me.book_management.dto.request.book.comment.CreateCommentRequest;
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
@Constraint(validatedBy = Create.CreateCommentValidator.class)
public @interface Create {

    String message() default "Yêu cầu tạo bình luận không hợp lệ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Component
    class CreateCommentValidator implements ConstraintValidator<Create, CreateCommentRequest> {

        @Autowired
        private AccountService accountService;
        private Account account;

        @Override
        public void initialize(Create constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
            this.account = accountService.findByUsername(CommonUtil.getCurrentAccount());
        }

        @Override
        public boolean isValid(CreateCommentRequest request, ConstraintValidatorContext context) {
            try {
                if (CommonUtil.isNull(this.account)) {
                    throw new UnauthorizedAccessException("Người dùng không tồn tại");
                }

                if (!CommonUtil.hasPermission(this.account, Constants.PERMISSION.CREATE_COMMENT)) {
                    throw new UnauthorizedAccessException("Bạn không có quyền tạo bình luận");
                }
                return true;
            } catch (Exception e) {
                if (e instanceof UnauthorizedAccessException) {
                    throw e;
                }
                throw new UnauthorizedAccessException("Lỗi kiểm tra quyền tạo bình luận: " + e.getMessage());
            }
        }
    }
}
