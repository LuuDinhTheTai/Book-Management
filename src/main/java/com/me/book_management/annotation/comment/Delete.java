package com.me.book_management.annotation.comment;

import com.me.book_management.constant.Constants;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.book.Comment;
import com.me.book_management.exception.UnauthorizedAccessException;
import com.me.book_management.service.AccountService;
import com.me.book_management.service.CommentService;
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
@Constraint(validatedBy = Delete.DeleteCommentValidator.class)
public @interface Delete {

    String message() default "Yêu cầu xóa bình luận không hợp lệ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Component
    class DeleteCommentValidator implements ConstraintValidator<Delete, Long> {

        @Autowired
        private AccountService accountService;
        @Autowired
        private CommentService commentService;
        private Account account;

        @Override
        public void initialize(Delete constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }

        @Override
        public boolean isValid(Long commentId, ConstraintValidatorContext context) {
            try {
                this.account = accountService.findByUsername(CommonUtil.getCurrentAccount());
                if (CommonUtil.isNull(this.account)) {
                    throw new UnauthorizedAccessException("Người dùng không tồn tại");
                }

                if (!CommonUtil.hasPermission(this.account, Constants.PERMISSION.DELETE_COMMENT)) {
                    throw new UnauthorizedAccessException("Bạn không có quyền xóa bình luận");
                }

                if (!isOwner(commentId)) {
                    throw new UnauthorizedAccessException("Bạn không có quyền xóa bình luận này. Chỉ chủ sở hữu mới có thể xóa bình luận.");
                }
                
                return true;
            } catch (Exception e) {
                if (e instanceof UnauthorizedAccessException) {
                    throw e;
                }
                throw new UnauthorizedAccessException("Lỗi kiểm tra quyền xóa bình luận: " + e.getMessage());
            }
        }

        private boolean isOwner(Long commentId) {
            try {
                Comment comment = commentService.find(commentId);
                return comment != null && comment.getAccount().getUsername().equals(this.account.getUsername());
            } catch (Exception e) {
                return false;
            }
        }
    }
}
