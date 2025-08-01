package com.me.book_management.annotation.address;

import com.me.book_management.constant.Constants;
import com.me.book_management.dto.request.account.address.UpdateAddressRequest;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.account.Address;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.exception.UnauthorizedAccessException;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.repository.account.AddressRepository;
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
@Constraint(validatedBy = Update.UpdateAddressValidator.class)
public @interface Update {

    String message() default "Yêu cầu cập nhật địa chỉ không hợp lệ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Component
    class UpdateAddressValidator implements ConstraintValidator<Update, UpdateAddressRequest> {

        @Autowired
        private AccountRepository accountRepository;
        @Autowired
        private AddressRepository addressRepository;

        @Override
        public void initialize(Update constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }

        @Override
        public boolean isValid(UpdateAddressRequest request, ConstraintValidatorContext context) {
            try {
                Account account = accountRepository.findByUsername(CommonUtil.getCurrentAccount())
                        .orElseThrow(() -> new NotFoundException("Account not found"));

                if (CommonUtil.isNull(account)) {
                    throw new UnauthorizedAccessException("Người dùng không tồn tại");
                }

                if (!CommonUtil.hasPermission(account, Constants.PERMISSION.UPDATE_ACCOUNT)) {
                    throw new UnauthorizedAccessException("Bạn không có quyền cập nhật địa chỉ");
                }

                return true;

            } catch (Exception e) {
                if (e instanceof UnauthorizedAccessException) {
                    throw e;
                }
                throw new UnauthorizedAccessException("Lỗi kiểm tra quyền cập nhật địa chỉ: " + e.getMessage());
            }
        }
    }
} 