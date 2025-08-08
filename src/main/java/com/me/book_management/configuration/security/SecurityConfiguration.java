package com.me.book_management.configuration.security;

import com.me.book_management.configuration.security.impl.*;
import com.me.book_management.constant.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final OAuth2UserServiceImpl oAuth2UserService;
    private final OAuth2AuthenticationSuccessHandlerImpl oAuth2AuthenticationSuccessHandlerImpl;
    private final PermissionFilterImpl permissionFilterImpl;

    public SecurityConfiguration(OAuth2UserServiceImpl oAuth2UserService,
                                 OAuth2AuthenticationSuccessHandlerImpl oAuth2AuthenticationSuccessHandlerImpl,
                                 PermissionFilterImpl permissionFilterImpl) {
        this.oAuth2UserService = oAuth2UserService;
        this.oAuth2AuthenticationSuccessHandlerImpl = oAuth2AuthenticationSuccessHandlerImpl;
        this.permissionFilterImpl = permissionFilterImpl;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                rq -> rq
                        .requestMatchers("/style.css").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/auth/signup",
                                "/auth/signin",
                                "/accounts/forgot-password",
                                "/auth/logout",
                                "/books/{id}",
                                "/books/",
                                "/categories/list",
                                "/categories/{id}",
                                "/oauth2/authorization/google",
                                "/login/oauth2/code/google").permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/auth/signup",
                                "/auth/signin",
                                "/accounts/forgot-password").permitAll()
                        .anyRequest().authenticated()
        );
//        http.formLogin(
//                login -> login
//                        .loginPage("/auth/signin")
//                        .successForwardUrl("/books/list")
//        );
        http.logout(
                logout -> logout
                        .logoutUrl("/auth/signout")
                        .logoutSuccessUrl("/auth/signin")
                        .invalidateHttpSession(true)           // Hủy session
                        .deleteCookies("JSESSIONID", Constants.COOKIE.ACCESS_TOKEN)
                        .permitAll()
        );
        http.oauth2ResourceServer(
                oauth2 -> oauth2
                        .jwt(
                                jwtConfigurer -> jwtConfigurer
                                        .jwtAuthenticationConverter(jwtAuthenticationConverter())
                                        .decoder(jwtDecoder())
                        )
                        .bearerTokenResolver(bearerTokenResolver())
        );
        http.oauth2Login(
                oauth2 -> oauth2
                        .loginPage("/oauth2/authorization/google")
                        .successHandler(oAuth2AuthenticationSuccessHandlerImpl)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService)
                        )
        );
        http.addFilterBefore(permissionFilterImpl, FilterSecurityInterceptor.class);
        http.csrf(
                c -> c.disable()
        );
        return http.build();
    }

    @Bean
    public BearerTokenResolver bearerTokenResolver() {
        return new BearerTokenResolverImpl();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        return new JwtAuthenticationConverterImpl();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return new JwtDecoderImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

