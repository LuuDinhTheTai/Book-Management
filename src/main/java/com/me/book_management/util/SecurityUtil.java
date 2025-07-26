package com.me.book_management.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static String getCurrentAccount() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
