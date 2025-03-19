package com.study.Board.user.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

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
