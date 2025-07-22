package com.me.book_management.util;

import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CookieUtil {

    public Cookie create(String name, String value) {
        log.info("(create) cookie: {}", name + " = " + value);

        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24); // 1 day

        return cookie;
    }
}
