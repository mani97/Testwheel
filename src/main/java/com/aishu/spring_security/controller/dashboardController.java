package com.aishu.spring_security.controller;

import com.aishu.spring_security.model.TestEntity;
import com.aishu.spring_security.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Controller
public class dashboardController {

    @GetMapping({"/", "/dashboar"})
    public String viewHomePage(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        return "dashboard";
    }
//    @GetMapping("/reset-password")
//    public String showresetPage(Model model) {
//        return "reset-password";
//    }

    @GetMapping("/reg")
    public String register(Model model) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        List<Map<String, Object>> countries = mapper.readValue(
//                new URL("https://raw.githubusercontent.com/samayo/country-json/master/src/country-by-calling-code.json"),
//                new TypeReference<List<Map<String, Object>>>() {}
//        );
//        model.addAttribute("countries", countries);
        return "register";
    }


    @GetMapping("/welcome")
    public String allTest(Model model) {

        // model.addAttribute("listEmployee",employeeService.getAllEmployee());
        return "welcome-loading";
    }

    @GetMapping("/testwheel")
    public String TestWheel(Model model) {

        // model.addAttribute("listEmployee",employeeService.getAllEmployee());
        return "welcome";
    }

    @GetMapping("/vid")
    public String layout(Model model) {

        // model.addAttribute("listEmployee",employeeService.getAllEmployee());
        return "layout";
    }

    @GetMapping("/alltest")
    public String createTest(Model model) {

        model.addAttribute("testEntity", new TestEntity());
        return "create-testcase";
    }



    @GetMapping("/createtest2")
    public String createProject2(Model model) {

        // model.addAttribute("listEmployee",employeeService.getAllEmployee());
        return "create-project-2";
    }

    @GetMapping("/testlist")
    public String TestList(Model model) {

        // model.addAttribute("listEmployee",employeeService.getAllEmployee());
        return "project-list";
    }

    @GetMapping("/testerr")
    public String Testerr(Model model) {

        // model.addAttribute("listEmployee",employeeService.getAllEmployee());
        return "testwheel-404-error-page";
    }

    @GetMapping("/timeout")
    public String Timeout(Model model) {

        // model.addAttribute("listEmployee",employeeService.getAllEmployee());
        return "testwheel-session-timed-out";
    }
}