package com.aishu.spring_security.model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "testrequest")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String testName;

    private String modules;

    private String tag;

    private String description;

    private String WebsiteUrl;

    private boolean backendApiTest;

    private String APIprotocol;

    // Relationship with ApkUpload entity
    @OneToMany(mappedBy = "testEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApkUpload> apkUpload;

}
