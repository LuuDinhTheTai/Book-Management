package com.me.book_management.configuration.security.impl;

import com.me.book_management.entity.rbac0.Action;
import com.me.book_management.entity.rbac0.Permission;
import com.me.book_management.entity.rbac0.Resource;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.repository.rbac0.ActionRepository;
import com.me.book_management.repository.rbac0.PermissionRepository;
import com.me.book_management.repository.rbac0.ResourceRepository;
import com.me.book_management.util.CommonUtil;
import com.me.book_management.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PermissionFilterImpl implements Filter {

    private final AccountRepository accountRepository;
    private final PermissionRepository permissionRepository;
    private final ActionRepository actionRepository;
    private final ResourceRepository resourceRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest &&
                response instanceof HttpServletResponse) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;

            String httpMethod = httpServletRequest.getMethod();
            String path = httpServletRequest.getRequestURI();

            List<String> publicPaths = List.of(
                    "/style.css", "/favicon.ico", "/js/", "/css/", "/images/", "/error"
            );
            boolean isPublicPath = publicPaths.stream().anyMatch(path::startsWith);

            if (isPublicPath) {
                chain.doFilter(request, response);
                return;
            }

            String accessToken = jwtUtil.getJwtFromCookies(httpServletRequest.getCookies());

            if (CommonUtil.isNull(accessToken)) {
                chain.doFilter(request, response);
                return;
            }

            String scope;
            try {
                scope = jwtUtil.extractScope(accessToken);

            } catch (ParseException e) {
                httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid token");
                return;
            }
            Action action = actionRepository.findByName(httpMethod.toLowerCase())
                    .orElseThrow(() -> new ServletException("Invalid action" + httpMethod));
            Resource resource = resourceRepository.findByName(path)
                    .orElseThrow(() -> new ServletException("Invalid resource" + path));

            Permission permission = permissionRepository.findByResourceAndAction(resource, action)
                    .orElseThrow(() -> new ServletException("Invalid permission" + httpMethod + " " + path));

            if (!scope.contains(permission.getName())) {
                httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Permission denied");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
