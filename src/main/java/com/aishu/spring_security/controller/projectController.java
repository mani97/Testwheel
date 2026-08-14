package com.aishu.spring_security.controller;


import com.aishu.spring_security.Repository.ProjectRepo;
import com.aishu.spring_security.model.Project;
import com.aishu.spring_security.model.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class projectController {

    @Autowired
    ProjectRepo projectRepo;

    @GetMapping("/createproject")
    public String createProject(Model model) {

        model.addAttribute("project",new Project());
        return "create-project";
    }

    @PostMapping("/saveproject")
    public String saveProject(@ModelAttribute("project") Project project, Model model, RedirectAttributes redirectAttributes) {
        Project saved = projectRepo.save(project); // persist entity
        redirectAttributes.addFlashAttribute("successMessage", "Project saved successfully!");
        return "redirect:/alltest"; // reload same view
    }


}
