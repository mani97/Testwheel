package com.aishu.spring_security.service;

import com.aishu.spring_security.Repository.TokenRepository;
import com.aishu.spring_security.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
@Service
public class JwtStoreService {


    @Autowired
    TokenRepository tokenRepository;


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

    public void revokeToken(String tokenValue) {
        tokenRepository.findByTokenValue(tokenValue).ifPresent(token -> {
            token.setRevoked(true);
            tokenRepository.save(token);
        });
    }
    public void revokeAllTokensForUser(String username) {
        List<Token> tokens = tokenRepository.findByUsername(username);
        tokens.forEach(token -> {
            token.setRevoked(true);
            tokenRepository.save(token);
        });
    }





}
