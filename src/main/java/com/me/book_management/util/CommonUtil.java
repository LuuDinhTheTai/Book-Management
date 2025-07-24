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

    public static boolean isOwner(Authentication authentication, Object resource) {
        return isOwner(authentication.getName(), resource);
    }

    public static boolean isOwner(String owner, Object resource) {
        if (resource instanceof Book) {
            return ((Book) resource).getAccount().getUsername().equals(owner);
        }
        return false;
    }
}
