package com.aishu.spring_security.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.apachecommons.CommonsLog;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Table(name="users")
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment
    private int id;


    @Column(nullable = false)
    private String firstName;


    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private String password;


    private String confirmPassword;

    private String pictureUrl;

}
