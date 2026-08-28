package com.aishu.spring_security.controller;

import com.aishu.spring_security.service.CookieUtil;
import com.aishu.spring_security.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class tokenController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/secure-api")
    public ResponseEntity<String> secureApi(@RequestHeader("Authorization") String bearerToken,
                                            Authentication authentication) {
        String token = bearerToken.replace("Bearer ", "");
        String username = authentication != null ? authentication.getName() : jwtService.extractUsername(token);

        if (jwtService.validateToken(token, username)) {
            String type = jwtService.getTokenType(token);
            return ResponseEntity.ok("Token valid, type: " + type);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }

    // Refresh token from cookie
    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie refreshCookie = CookieUtil.getCookie(request, "refreshToken");
        if (refreshCookie == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No refresh token found");
        }

        String refreshToken = refreshCookie.getValue();

        // Validate refresh token
        if (jwtService.isTokenExpired(refreshToken) ||
                !"refresh".equals(jwtService.getTokenType(refreshToken))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
        }

        String username = jwtService.extractUsername(refreshToken);
        String newAccessToken = jwtService.generateAccessToken(username);

        // Replace old access token cookie
        response.addCookie(CookieUtil.createAccessTokenCookie(newAccessToken));

        return ResponseEntity.ok("Access token refreshed");
    }
}

