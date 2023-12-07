package com.hwamok.auth.service;

import org.springframework.data.util.Pair;

public interface AuthService {
    Pair<String, String> login(String login, String password);
}
