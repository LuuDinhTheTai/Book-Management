package com.me.book_management.configuration.security.impl;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

public class JwtAuthenticationConverterImpl extends JwtAuthenticationConverter {

    private JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    public JwtAuthenticationConverterImpl() {
        super();
        this.jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        super.setJwtGrantedAuthoritiesConverter(this.jwtGrantedAuthoritiesConverter);
    }
}
