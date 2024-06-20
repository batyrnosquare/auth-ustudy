package dev.batyrnosquare.auth_ustudy.data;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ADMIN,
    STUDENT,
    TEACHER,
    MASTER,
    CUSTOMER;


    @Override
    public String getAuthority() {
        return null;
    }
}
