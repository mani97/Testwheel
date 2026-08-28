package com.testwheel.test_service.controller;



import com.testwheel.test_service.Repository.TestRepository;
import com.testwheel.test_service.model.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class testListController {


//    @Autowired
//    UserClient userClient; // Feign client instead of UserRepo

    @Autowired
    TestRepository testRepository;

    @GetMapping("/testlist")
    public String TestList(Model model) {
        model.addAttribute("testEntity",new TestEntity());
        long testCount = testRepository.count();

//        // Call User Service API instead of userRepo
//        long userCount = userClient.getUserCount(); // call User Service

        //long userCount = userRepo.count();// built-in JPA count()
        System.out.println(testCount);
        //System.out.println(userCount);

        model.addAttribute("testCount", testCount);
        //model.addAttribute("userCount", userCount);


 return "project-list";
    }
    @GetMapping("testrequest/count")
    public long getTestRequestCount() {
        return testRepository.count();
    }



}
