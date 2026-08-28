package com.aishu.spring_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class dashboardController {


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

    @GetMapping({"/","vid"})
    public String layout(Model model) {

        // model.addAttribute("listEmployee",employeeService.getAllEmployee());
        return "layout";
    }


    @GetMapping("/createtest2")
    public String createProject2(Model model) {

        // model.addAttribute("listEmployee",employeeService.getAllEmployee());
        return "create-project-2";
    }



    @GetMapping("/err")
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