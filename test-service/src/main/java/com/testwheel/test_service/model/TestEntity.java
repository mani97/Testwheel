package com.testwheel.test_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "testrequest")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testName;

    private String modules;

    private String tag;

    private String description;

    private String websiteUrl;

    private boolean backendApiTest;

    private String apiProtocol;


}
