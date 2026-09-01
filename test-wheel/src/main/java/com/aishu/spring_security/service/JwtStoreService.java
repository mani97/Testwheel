package com.aishu.spring_security.service;

import com.aishu.spring_security.Repository.TokenRepository;
import com.aishu.spring_security.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JwtStoreService {

    @Autowired
    TokenRepository tokenRepository;

    public void storeToken(String username, String token) {
        findstoreRefreshToken(username, token, LocalDateTime.now().plusHours(24));
    }

    public String getToken(String username) {
        return findRefreshTokenByUser(username);
    }

    public void removeToken(String username) {
        revokeAllTokensForUser(username);
    }

    public boolean hasToken(String username) {
        if (username == null) return false;
        List<Token> tokens = tokenRepository.findByUsername(username);
        return tokens.stream().anyMatch(t -> !t.isRevoked());
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

    public String findRefreshTokenByUser(String userName) {
        if (userName == null) return null;
        List<Token> tokens = tokenRepository.findByUsername(userName);
        return tokens.stream()
                .filter(t -> !t.isRevoked() && "refresh".equals(t.getTokenType()))
                .map(Token::getTokenValue)
                .findFirst()
                .orElse(null);
    }

    public void findstoreRefreshToken(String userName, String newRefreshToken, LocalDateTime localDateTime) {
        if (userName == null) return;
        List<Token> tokens = tokenRepository.findByUsername(userName);
        Token token;
        if (!tokens.isEmpty()) {
            token = tokens.get(0);
        } else {
            token = new Token();
        }
        token.setUsername(userName);
        token.setTokenValue(newRefreshToken);
        token.setTokenType("refresh");
        token.setExpiresAt(localDateTime);
        token.setRevoked(false);
        tokenRepository.save(token);
    }
}

