package com.aishu.spring_security.controller;


import com.aishu.spring_security.Repository.TestRepository;
import com.aishu.spring_security.Repository.UserRepo;
import com.aishu.spring_security.dao.UserPrinciple;
import com.aishu.spring_security.model.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;


@Controller
public class testListController {

    @Autowired
    TestRepository testRepository;

    @Autowired
    UserRepo userRepo;



    @GetMapping("/testlist")
    public String TestList(Model model) {
        model.addAttribute("testEntity",new TestEntity());
        long testCount = testRepository.count();
        long userCount = userRepo.count();// built-in JPA count()
        System.out.println(testCount);
        System.out.println(userCount);




        model.addAttribute("testCount", testCount);
        model.addAttribute("userCount", userCount);


 return "project-list";
    }
    @GetMapping("/testrequest/count")
    public Map<String, Long> getTestRequestCount() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("users", userRepo.count());
        counts.put("tests", testRepository.count());
        return counts;
    }


}
