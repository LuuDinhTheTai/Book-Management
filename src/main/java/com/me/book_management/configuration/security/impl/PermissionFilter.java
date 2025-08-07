package com.me.book_management.configuration.security.impl;

import com.me.book_management.entity.rbac0.Action;
import com.me.book_management.entity.rbac0.Permission;
import com.me.book_management.entity.rbac0.Resource;
import com.me.book_management.repository.rbac0.ActionRepository;
import com.me.book_management.repository.rbac0.PermissionRepository;
import com.me.book_management.repository.rbac0.ResourceRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class PermissionFilter implements Filter {

    private final PermissionRepository permissionRepository;
    private final ActionRepository actionRepository;
    private final ResourceRepository resourceRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest &&
                response instanceof HttpServletResponse) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;

            String httpMethod = httpServletRequest.getMethod();
            String path = httpServletRequest.getRequestURI();

            Action action = actionRepository.findByName(httpMethod.toLowerCase())
                    .orElseThrow(() -> new ServletException("Invalid action"));
            Resource resource = resourceRepository.findByName(path)
                    .orElseThrow(() -> new ServletException("Invalid resource"));

            Permission permission = permissionRepository.findByResourceAndAction(resource, action)
                    .orElseThrow(() -> new ServletException("Invalid permission"));
        }

        chain.doFilter(request, response);
    }
}
