package com.me.book_management.configuration.security.jwt;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

@Component
public class CustomJwtDecoder implements JwtDecoder {

    @Override
    public Jwt decode(String token) throws JwtException {
        return null;
    }
}
