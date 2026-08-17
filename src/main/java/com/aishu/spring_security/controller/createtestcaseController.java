package com.aishu.spring_security.controller;



import com.aishu.spring_security.Repository.TestRepository;
import com.aishu.spring_security.Repository.ApkRepo;
import com.aishu.spring_security.model.ApkUpload;
import com.aishu.spring_security.model.TestEntity;



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


        @PostMapping("/createtest")
        public String createTestCase(@ModelAttribute("testEntity") TestEntity testEntity,
                                     BindingResult result, Model model) {
//            if (result.hasErrors()) {
//                return "create-testcase"; // redisplay form with errors
//            }
            testRepository.save(testEntity);
            // After saving, redirect to test list page
            return "redirect:/alltest";
        }

    @PostMapping("/uploadApk")
    public String uploadApk(@RequestParam("file") MultipartFile file,
                            @RequestParam("testId") int testId) throws IOException {
        TestEntity test = testRepository.findById(testId).orElseThrow();

        // Save file to disk
        String uploadDir = "uploads/apk/";
        Path path = Paths.get(uploadDir + file.getOriginalFilename());
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        // Parse APK metadata
        try (ApkFile apkFile = new ApkFile(path.toFile())) {
            ApkMeta meta = apkFile.getApkMeta();

            ApkUpload apk = new ApkUpload();
            apk.setFileName(file.getOriginalFilename());
            apk.setFilePath(path.toString());
            apk.setFileSize(file.getSize());
            apk.setUploadedAt(LocalDateTime.now());
            apk.setTestEntity(test);

            // Metadata
            apk.setPackageName(meta.getPackageName());
            apk.setVersionCode(meta.getVersionCode());
            apk.setVersionName(meta.getVersionName());
            apk.setPermissions(meta.getUsesPermissions().toString());

            apkRepo.save(apk);
        }

        return "redirect:/dashboard";
    }




    }

