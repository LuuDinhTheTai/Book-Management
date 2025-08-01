package com.me.book_management.annotation.category;

import com.me.book_management.constant.Constants;
import com.me.book_management.dto.request.book.CreateBookRequest;
import com.me.book_management.dto.request.book.category.CreateCategoryRequest;
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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Create.CreateCategoryValidator.class)
public @interface Create {

    String message() default "Yêu cầu tạo sách không hợp lệ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Component
    class CreateCategoryValidator implements ConstraintValidator<Create, CreateCategoryRequest> {

        @Autowired
        private AccountService accountService;

        @Override
        public void initialize(Create constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }

        @Override
        public boolean isValid(CreateCategoryRequest request, ConstraintValidatorContext context) {
            try {
                Account account = accountService.findByUsername(CommonUtil.getCurrentAccount());
                if (CommonUtil.isNull(account)) {
                    throw new UnauthorizedAccessException("Người dùng không tồn tại");
                }

                if (!CommonUtil.hasPermission(account, Constants.PERMISSION.CREATE_CATEGORY)) {
                    throw new UnauthorizedAccessException("Bạn không có quyền tạo danh mục");
                }
                return true;

            } catch (Exception e) {
                if (e instanceof UnauthorizedAccessException) {
                    throw e;
                }
                throw new UnauthorizedAccessException("Lỗi kiểm tra quyền tạo sách: " + e.getMessage());
            }
        }
    }
}
