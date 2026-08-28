package com.testwheel.test_service.service;

import com.testwheel.test_service.Dto.ProjectDto;
import com.testwheel.test_service.model.Project;
import org.springframework.stereotype.Service;


@Service
public class ProjectMapper {

    public static ProjectDto toDto(Project projects) {
        return new ProjectDto(
                projects.getId(),
                projects.getProjectName(),
                projects.getCreatedBy(),
                projects.getProjectUrl(),
                projects.getUsername()
        );
    }

    public static Project toEntity(ProjectDto dto) {
        return new Project(
                dto.getId(),
                dto.getProjectName(),
                dto.getCreatedBy(),
                dto.getProjectUrl(),
                dto.getUsername()
        );
    }
}
