package com.testwheel.test_service.controller;


import com.testwheel.test_service.Dto.ApkUploadDto;
import com.testwheel.test_service.Dto.TestEntityDto;
import com.testwheel.test_service.Repository.ApkRepo;
import com.testwheel.test_service.Repository.TestRepository;
import com.testwheel.test_service.model.TestEntity;
import com.testwheel.test_service.model.ApkUpload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TestEventConsumer {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private ApkRepo apkRepo;

    @KafkaListener(
            topics = "test-created-topic",
            groupId = "test-service-group",
            containerFactory = "testEntityKafkaListenerContainerFactory"
    )
    public void handleTestCreated(TestEntityDto dto) {
        System.out.println("Consumed TestEntityDto: " + dto);

        TestEntity entity = new TestEntity();
        entity.setTestName(dto.getTestName());
        entity.setModules(dto.getModules());
        entity.setDescription(dto.getDescription());
        entity.setTag(dto.getTag());
        entity.setWebsiteUrl(dto.getWebsiteUrl());
        entity.setBackendApiTest(dto.isBackendApiTest());
        entity.setApiProtocol(dto.getApiProtocol());

        testRepository.save(entity);
    }

    @KafkaListener(
            topics = "apk-uploaded-topic",
            groupId = "test-service-group",
            containerFactory = "apkUploadKafkaListenerContainerFactory"
    )
    public void handleApkUploaded(ApkUploadDto dto) {
        System.out.println("Consumed ApkUploadDto: " + dto);

        ApkUpload apkUpload = new ApkUpload();
        apkUpload.setFileName(dto.getFileName());
        apkUpload.setFileSize(dto.getFileSize());
        apkUpload.setUploadedAt(dto.getUploadedAt());
        apkUpload.setTestEntityId(dto.getTestEntityId());

        apkRepo.save(apkUpload);
    }
}
