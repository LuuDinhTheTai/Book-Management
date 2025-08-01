package com.me.book_management.configuration.security.impl;

import com.me.book_management.exception.BadRequestException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, 
                       HttpServletResponse response, 
                       AuthenticationException authException) throws IOException, ServletException {
    throw new BadRequestException("Invalid token");
  }
}
