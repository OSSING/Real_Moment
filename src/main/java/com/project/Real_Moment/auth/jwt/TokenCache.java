package com.project.Real_Moment.auth.jwt;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenCache {
    private final Map<String, String> tokenStore = new ConcurrentHashMap<>();
    private final Map<String, String> tokenBlackList = new ConcurrentHashMap<>();

    public void setToken(String key, String refreshToken) {
        tokenStore.put(key, refreshToken);
    }

    public void setBlackList(String key, String refreshToken) {
        tokenBlackList.put(key, refreshToken);
    }

    public String getBlackList(String key) {
        return tokenBlackList.get(key);
    }

    public String getToken(String key) {
        return tokenStore.get(key);
    }

    public void removeToken(String key) {
        tokenStore.remove(key);
    }
}
