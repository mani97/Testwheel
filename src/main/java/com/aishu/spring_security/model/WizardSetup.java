package com.aishu.spring_security.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "wizards")
@NoArgsConstructor
@AllArgsConstructor
public class WizardSetup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;
    private String organizationName;
    private String teamSize;
    private String platform;

    // getters and setters
}
