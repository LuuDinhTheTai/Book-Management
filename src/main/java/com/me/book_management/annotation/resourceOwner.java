package com.me.book_management.annotation;

import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.account.Address;
import com.me.book_management.entity.book.Book;
import com.me.book_management.entity.book.Category;
import com.me.book_management.entity.book.Comment;
import com.me.book_management.entity.cart.Cart;
import com.me.book_management.exception.BadRequestException;
import com.me.book_management.exception.UnauthorizedException;
import com.me.book_management.repository.CategoryRepository;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.repository.account.AddressRepository;
import com.me.book_management.repository.book.BookRepository;
import com.me.book_management.repository.book.CommentRepository;
import com.me.book_management.repository.cart.CartRepository;
import com.me.book_management.util.CommonUtil;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = resourceOwner.resourceOwnerValidator.class)
public @interface resourceOwner {

    String message() default "You are not resource owner";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String instance();

    @Component
    @RequiredArgsConstructor
    @Slf4j
    class resourceOwnerValidator implements ConstraintValidator<resourceOwner, Long> {

        private final AccountRepository accountRepository;
        private final AddressRepository addressRepository;
        private final BookRepository bookRepository;
        private final CartRepository cartRepository;

        private String instance;

        @Override
        public void initialize(resourceOwner constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
            this.instance = constraintAnnotation.instance();
        }

        @Override
        public boolean isValid(Long id, ConstraintValidatorContext context) {
            if (CommonUtil.isNull(id)) {
                throw new BadRequestException("Invalid resource id");
            }
            Account currentAccount = accountRepository.findByUsername(CommonUtil.getCurrentAccount())
                    .orElseThrow(() -> new UnauthorizedException("You are not authorized"));

            Long currentAccountId = currentAccount.getId();

            String accountInstance = Account.class.getSimpleName();
            String addressInstance = Address.class.getSimpleName();
            String bookInstance = Book.class.getSimpleName();
            String cartInstance = Cart.class.getSimpleName();

            if (CommonUtil.isEqual(this.instance, accountInstance)) {
                if (!CommonUtil.isEqual(currentAccountId, id)) {
                    throw new UnauthorizedException("You are not resource owner");
                }
                return true;
            }

            if (CommonUtil.isEqual(this.instance, addressInstance)) {
                Address address = addressRepository.findById(id)
                        .orElseThrow(() -> new BadRequestException("Invalid resource id"));

                if (!CommonUtil.isEqual(currentAccountId, address.getAccount().getId())) {
                    throw new UnauthorizedException("You are not resource owner");
                }
                return true;
            }

            if (CommonUtil.isEqual(this.instance, bookInstance)) {
                Book book = bookRepository.findById(id)
                        .orElseThrow(() -> new BadRequestException("Invalid resource id"));

                if (!CommonUtil.isEqual(currentAccountId, book.getAccount().getId())) {
                    throw new UnauthorizedException("You are not resource owner");
                }
                return true;
            }

            if (!CommonUtil.isEqual(this.instance, cartInstance)) {
                Cart cart = cartRepository.findById(id)
                        .orElseThrow(() -> new BadRequestException("Invalid resource id"));

                if (!CommonUtil.isEqual(currentAccountId, cart.getAccount().getId())) {
                    throw new UnauthorizedException("You are not resource owner");
                }
                return true;
            }

            return true;
        }
    }
}
