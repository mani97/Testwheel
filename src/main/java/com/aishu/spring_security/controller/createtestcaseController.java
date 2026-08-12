package com.aishu.spring_security.controller;



import com.aishu.spring_security.Repository.TestRepository;
import com.aishu.spring_security.model.TestEntity;
import com.aishu.spring_security.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class createtestcaseController {

        @Autowired
    private TestRepository testRepository;

    @GetMapping("/alltest1")
    public String testcase(Model model) {
        model.addAttribute("testEntity", new TestEntity());
        return "create-testcase";
    }



        @PostMapping("/createtest")
        public String createTestCase(@ModelAttribute("testEntity") TestEntity testEntity,
                                     BindingResult result,Model model) {
//            if (result.hasErrors()) {
//                return "create-testcase"; // redisplay form with errors
//            }
            testRepository.save(testEntity);
            // After saving, redirect to test list page
            return "redirect:/alltest";
        }
    }

