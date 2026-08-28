package com.testwheel.test_service.controller;

import com.netflix.discovery.converters.Auto;
import com.testwheel.test_service.Dto.ProjectDto;
import com.testwheel.test_service.Repository.TestRepository;
import com.testwheel.test_service.service.ProjectMapper;
import com.testwheel.test_service.Repository.ProjectRepository;
import com.testwheel.test_service.model.Project;


import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class projectController {

    @Autowired
    ProjectRepository projectRepo;
    @Autowired
    TestRepository testRepository;

    @PostMapping("/saveproject")
    public ProjectDto saveProject(@RequestBody ProjectDto project, Model model, RedirectAttributes redirectAttributes, Authentication authentication) {


        Project newproject = new Project();

            newproject.setProjectName(project.getProjectName());
            newproject.setProjectUrl(project.getProjectUrl());
            newproject.setCreatedBy(project.getCreatedBy());
            newproject.setId(project.getId());

        Project saved = projectRepo.save(newproject); // persist entity

        ProjectDto response = new ProjectDto();
        response.setProjectName(saved.getProjectName());
        response.setProjectUrl(saved.getProjectUrl());
        response.setId(saved.getId());
        response.setCreatedBy(saved.getCreatedBy());
        response.setUsername(response.getUsername());

        return response; // reload same view
    }


    @GetMapping("/projects")
    public List<ProjectDto> getProjects() {
        List<Project> projects =projectRepo.findAll();

        // Convert to DTOs
        List<ProjectDto> dtoList = projects.stream()
                .map(ProjectMapper::toDto)
                .toList();

        return dtoList;
    }




}
