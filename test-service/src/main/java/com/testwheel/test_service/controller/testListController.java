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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class testListController {

    private static final Logger log = LoggerFactory.getLogger(testListController.class);

    @Autowired
    TestRepository testRepository;

    @GetMapping("/testrequest/count")
    public long getTestRequestCount() {
        return testRepository.count();
    }
}
