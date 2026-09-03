package com.aishu.spring_security.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
        private String name;
        private String email;
        private String picture;
        private String firstLetter;
    }
