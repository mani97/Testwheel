//package com.aishu.spring_security.service;
//
//
//import com.aishu.spring_security.client.TestInterface;
//import com.aishu.spring_security.Dto.TestEntityDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//@Service
//public class TestService {
//
//    @Autowired
//    private TestInterface testInterface;
//
//    public TestEntityDto createTest(TestEntityDto dto) {
//        return testInterface.createTest(dto);
//    }
//
//    public void uploadApk(MultipartFile apkFile, int testId) {
//        testInterface.uploadApk(apkFile, testId);
//    }
//}
//
