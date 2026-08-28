package com.aishu.spring_security.controller;

import com.aishu.spring_security.Dto.ApkUploadDto;
import com.aishu.spring_security.Dto.TestEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;


@Controller
public class createtestcaseController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping("/alltest")
    public String showForm(Model model) {
        model.addAttribute("testEntity", new TestEntityDto());
        return "create-testcase"; // Thymeleaf template lives in A
    }

    @PostMapping("/createtest")
    public String createTest(@ModelAttribute TestEntityDto testEntityDto,
                             @RequestParam(value="apk", required=false) MultipartFile apkFile) {

        // Publish TestCreatedEvent
        kafkaTemplate.send("test-created-topic",testEntityDto);

        // Publish ApkUploadedEvent if file exists
        if (apkFile != null && !apkFile.isEmpty()) {
            ApkUploadDto dto = new ApkUploadDto();
            dto.setFileName(apkFile.getOriginalFilename());
            dto.setFileSize(apkFile.getSize());
            dto.setTestEntityId(testEntityDto.getId());
            dto.setUploadedAt(LocalDateTime.now());

            kafkaTemplate.send("apk-uploaded-topic", dto);
        }

        return "redirect:/alltest";
    }
}
