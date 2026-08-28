package com.testwheel.test_service.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestEntityDto {
    private Long id;

    private String testName;

    private String modules;

    private String tag;

    private String description;

    private String websiteUrl;

    private boolean backendApiTest;

    private String apiProtocol;

}
