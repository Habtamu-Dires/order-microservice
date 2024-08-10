package com.hab.gateway.config;


import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeyCloakAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {


    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {

        return new JwtAuthenticationToken(
            jwt,
            Stream.concat(
                new JwtGrantedAuthoritiesConverter().convert(jwt).stream(),
                extractResourceRole(jwt).stream()
            ).collect(Collectors.toSet())
        );
    }

    private Collection<? extends GrantedAuthority> extractResourceRole(Jwt jwt){
        var resourceAccess = new HashMap<>(jwt.getClaim("resource_access"));
        var external = (Map<String, List<String>>)resourceAccess.get("account");
        var roles = external.get("roles");
        return  roles.stream()
                .map(role -> new SimpleGrantedAuthority(
                        "ROLE_" +role.replace("-","_"))
                )
                .collect(Collectors.toSet());
    }
}