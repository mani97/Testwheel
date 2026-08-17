package com.aishu.spring_security.controller;

import com.aishu.spring_security.Repository.UserRepo;
import com.aishu.spring_security.dao.UserPrinciple;
import com.aishu.spring_security.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;



import com.aishu.spring_security.Repository.ProjectRepository;
import com.aishu.spring_security.model.Project;
import com.aishu.spring_security.model.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class projectController {

    @Autowired
    ProjectRepository projectRepo;

    @Autowired
    UserRepo userRepo;

    @GetMapping("/createproject")
    public String createProject(Model model) {

        model.addAttribute("project",new Project());
        return "create-project";
    }

    @PostMapping("/saveproject")
    public String saveProject(@ModelAttribute("project") Project project, Model model, RedirectAttributes redirectAttributes,Authentication authentication,@ModelAttribute("currentUser") User currentUser) {

            // Inject global model attribute into entity
            project.setCreatedBy(currentUser.getFirstName());
            project.setUsername(currentUser.getUsername());

        Project saved = projectRepo.save(project); // persist entity
        redirectAttributes.addFlashAttribute("successMessage", "Project saved successfully!");
        return "redirect:/createproject"; // reload same view
    }


    @GetMapping("/projects")
    public String getProjects(Model model) {
        List<Project> projects = projectRepo.findAll();
        model.addAttribute("projects", projects);
//        return "projectDropdown::dashboard";
        return "dashboard"; // Thymeleaf fragment
    }

}
