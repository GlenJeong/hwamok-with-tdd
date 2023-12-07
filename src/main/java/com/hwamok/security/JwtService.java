package com.hwamok.security;

import jakarta.servlet.http.HttpServletRequest;

public interface JwtService {
    String issue(long userId, JwtType type);

    String resolveToken(HttpServletRequest request);

    boolean validate(String token);

    Long getId(String token);
}
