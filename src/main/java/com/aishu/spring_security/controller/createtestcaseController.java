package com.aishu.spring_security.controller;



import com.aishu.spring_security.Repository.TestRepository;
import com.aishu.spring_security.Repository.ApkRepo;
import com.aishu.spring_security.model.ApkUpload;
import com.aishu.spring_security.model.TestEntity;


import net.dongliu.apk.parser.ApkParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;

@Controller
public class createtestcaseController {

        @Autowired
    private TestRepository testRepository;

        @Autowired
        ApkRepo apkRepo;


    @GetMapping("/alltest")
    public String createTest(Model model) {

        model.addAttribute("testEntity", new TestEntity());

        return "create-testcase";
    }


        @PostMapping("/createtest1")
        public String createTestCase(@ModelAttribute("testEntity") TestEntity testEntity,
                                     BindingResult result, Model model) {

            testRepository.save(testEntity);
            // After saving, redirect to test list page
            return "redirect:/alltest";
        }


    @PostMapping("/createtest")
    public String createTest(@ModelAttribute TestEntity testEntity,
                             @RequestParam(value="apk", required=false) MultipartFile apkFile) throws IOException {
        // Save TestEntity to DB
        testRepository.save(testEntity);

        // Handle APK file
        if (!apkFile.isEmpty()) {
            Path path = Paths.get("uploads/" + apkFile.getOriginalFilename());
            Files.createDirectories(path.getParent());
            Files.write(path, apkFile.getBytes());

            // Extract metadata
            try (ApkParser apkParser = new ApkParser(path.toFile())) {
                ApkMeta meta = apkParser.getApkMeta();

                // Example: attach metadata to your entity or a separate table
                ApkUpload apkUpload = new ApkUpload();
                apkUpload.setFileName(apkFile.getOriginalFilename());
                apkUpload.setFilePath(path.toString());
                apkUpload.setFileSize(apkFile.getSize());
                apkUpload.setUploadedAt(LocalDateTime.now());
                apkUpload.setTestEntity(testEntity);

                apkUpload.setPackageName(meta.getPackageName());
                apkUpload.setVersionName(meta.getVersionName());
                apkUpload.setVersionCode(Math.toIntExact(meta.getVersionCode()));
                apkUpload.setPermissions(String.join(",", meta.getUsesPermissions()));

                apkRepo.save(apkUpload);
            }
        }

        return "redirect:/alltest";
    }





}

