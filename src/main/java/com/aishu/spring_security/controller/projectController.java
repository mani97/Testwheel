package com.aishu.spring_security.controller;


import com.aishu.spring_security.Repository.ProjectRepo;
import com.aishu.spring_security.model.Project;
import com.aishu.spring_security.model.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class projectController {

    @Autowired
    ProjectRepo projectRepo;

    @GetMapping("/createproject")
    public String createProject(Model model) {

        //model.addAttribute("testEntity",new TestEntity());
        return "create-project";
    }

    @PostMapping("/saveproject")
    public String saveProject(@ModelAttribute("project") Project project, Model model) {
        projectRepo.save(project); // persist entity
        model.addAttribute("successMessage", "Project saved successfully!");
        return "project-form"; // return the same Thymeleaf view (no redirect)
    }


}
