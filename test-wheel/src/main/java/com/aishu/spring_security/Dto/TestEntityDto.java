package com.aishu.spring_security.Dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestEntityDto {

        private int id;

        private String testName;

        private String modules;

        private String tag;

        private String description;

        private String websiteUrl;

        private boolean backendApiTest;

        private String apiProtocol;

}
