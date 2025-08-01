package com.me.book_management.configuration.security;

import com.me.book_management.configuration.security.impl.BearerTokenResolverImpl;
import com.me.book_management.configuration.security.impl.JwtAuthenticationConverterImpl;
import com.me.book_management.configuration.security.impl.JwtDecoderImpl;
import com.me.book_management.configuration.security.impl.UserDetailsServiceImpl;
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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

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
                                "/categories/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/auth/signup",
                                "/auth/signin",
                                "/accounts/forgot-password").permitAll()
                        .anyRequest().authenticated()
        );
        http
//                .formLogin(form -> form
//                        .loginPage("/auth/signin")
//                        .defaultSuccessUrl("/books/")
//                        .permitAll()
//                )
                .logout(logout -> logout
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

