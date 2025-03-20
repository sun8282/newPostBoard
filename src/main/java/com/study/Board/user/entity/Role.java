package com.study.Board.user.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    BANNED,
    MANAGER,
    ADMIN;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }

}
