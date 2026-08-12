package com.aishu.spring_security.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "oauth_accounts")
public class OauthAccount {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider;
    private String providerUserId;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
