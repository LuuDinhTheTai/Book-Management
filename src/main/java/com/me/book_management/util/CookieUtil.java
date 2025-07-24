package com.me.book_management.util;

import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CookieUtil {

    @Value("${cookie.httpOnly}")
    private boolean httpOnly;
    @Value("${cookie.path}")
    private String path;
    @Value("${cookie.maxAge}")
    private int maxAge;

    public Cookie create(String name, String value) {
        log.info("(create) cookie: {}", name + " = " + value);

        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(httpOnly);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);

        return cookie;
    }
}
