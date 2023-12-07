package com.hwamok.security;

import lombok.Getter;
import net.bytebuddy.implementation.bind.annotation.Empty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


public class HwamokUser implements UserDetails {
    @Getter // id만 겟터
    private Long id;

    public HwamokUser(Long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // 사용자 Role에 사용함 user, admin
        return Collections.emptyList(); // 빈 배열
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() { // 계정이 만료 되었니?
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 아이디가 잠겨 있니라고 물어보는 것
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 인증하는 것이 만료되었니?
        return true;
    }

    @Override
    public boolean isEnabled() { // 계정을 활성화
        return true;
    }
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }
}
