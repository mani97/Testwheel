package com.aishu.spring_security.dao;

import com.aishu.spring_security.model.Token;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

public class UserRegistrationDto {

    private String firstName;

    private String lastName;

    private String username;

    private String phone;

    private String password;

    private String pictureUrl;

    private List<Token> token = new ArrayList<>();

}
