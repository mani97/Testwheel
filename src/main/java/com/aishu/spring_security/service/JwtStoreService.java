package com.aishu.spring_security.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
@Service
public class JwtStoreService {

    // Thread-safe map: username → JWT
    private final Map<String, String> jwtMap = new ConcurrentHashMap<>();

    public void storeToken(String username, String token) {
        jwtMap.put(username, token);
    }

    public String getToken(String username) {
        return jwtMap.get(username);
    }

    public void removeToken(String username) {
        jwtMap.remove(username);
    }

    public boolean hasToken(String username) {
        return jwtMap.containsKey(username);
    }
}
