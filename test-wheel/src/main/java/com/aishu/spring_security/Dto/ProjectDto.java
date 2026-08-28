package com.aishu.spring_security.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private int id;
    private String projectName;
    private String createdBy;
    private String projectUrl;
    private String username;
}
