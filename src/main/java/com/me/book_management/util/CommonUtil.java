package com.me.book_management.util;

import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.book.Book;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CommonUtil {

    public static String getCurrentAccount() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static boolean isNull(Object object) {
        return object == null;
    }

    public static boolean isDeleted(Account account) {
        return account != null && account.getDeletedAt() != null && account.getDeletedBy() != null;
    }
}
