package com.aishu.spring_security.controller;


import com.aishu.spring_security.Dto.TestEntityDto;

import com.aishu.spring_security.Repository.UserRepo;

import com.aishu.spring_security.client.TestInterface;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class testListController {

    private static final Logger log = LoggerFactory.getLogger(testListController.class);

    @Autowired
    UserRepo userRepo;
    @Autowired
    TestInterface testInterface;



    @GetMapping("/testlist")
    public String TestList(Model model) {
        model.addAttribute("testEntity",new TestEntityDto());
        long testCount = testInterface.getTestCount();
        long userCount = userRepo.count();// built-in JPA count()
        log.info("testCount: {}, userCount: {}", testCount, userCount);




        model.addAttribute("testCount", testCount);
        model.addAttribute("userCount", userCount);


 return "project-list";
    }

//    @GetMapping("/testrequest/count")
//    @ResponseBody
//    public Map<String, Long> getTestRequestCount() {
//        Map<String, Long> counts = new HashMap<>();
//        counts.put("users", userRepo.count());
//        counts.put("tests", testInterface.getTestCount());
//        return counts;
//    }


}
