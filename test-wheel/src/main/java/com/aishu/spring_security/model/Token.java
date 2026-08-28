package com.aishu.spring_security.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Table(name="tokens")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider;  //google,github

    private String tokenType; // "access" or "refresh"

    private String tokenValue; //access,refresh
    private LocalDateTime expiresAt;
    private boolean revoked = false;

    private String username;


}
