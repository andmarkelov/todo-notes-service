package com.wmrk.todo.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER");

    private final String vale;

    @Override
    public String getAuthority() {
        return vale;
    }

}
