package com.testwheel.test_service.controller;


import com.testwheel.test_service.Dto.ApkUploadDto;
import com.testwheel.test_service.Dto.TestEntityDto;
import com.testwheel.test_service.Repository.ApkRepo;
import com.testwheel.test_service.Repository.TestRepository;
import com.testwheel.test_service.model.TestEntity;
import com.testwheel.test_service.model.ApkUpload;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TestEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(TestEventConsumer.class);

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private ApkRepo apkRepo;




    // --- TestEntity Consumer ---
    @CircuitBreaker(name = "testServiceCB", fallbackMethod = "fallbackTestCreated")
    @Retry(name = "testServiceCB")
    @Bulkhead(name = "testServiceCB", type = Bulkhead.Type.SEMAPHORE)
    @RateLimiter(name = "testServiceCB")
    @KafkaListener(
            topics = "test-created-topic",
            groupId = "test-service-group",
            containerFactory = "testEntityKafkaListenerContainerFactory"
    )
    public void handleTestCreated(TestEntityDto dto) {
        log.info("Consumed TestEntityDto: {}", dto);

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

    public void fallbackTestCreated(TestEntityDto dto, Throwable t) {
        log.error("Circuit breaker triggered for TestEntityDto: {}, Error: {}", dto, t.getMessage());
        // Optionally route to Dead Letter Queue or log for retry
    }

    // --- ApkUpload Consumer ---
    @CircuitBreaker(name = "apkServiceCB", fallbackMethod = "fallbackApkUploaded")
    @Retry(name = "apkServiceCB")
    @Bulkhead(name = "apkServiceCB", type = Bulkhead.Type.SEMAPHORE)
    @RateLimiter(name = "apkServiceCB")
    @KafkaListener(
            topics = "apk-uploaded-topic",
            groupId = "test-service-group",
            containerFactory = "apkUploadKafkaListenerContainerFactory"
    )
    public void handleApkUploaded(ApkUploadDto dto) {
        log.info("Consumed ApkUploadDto: {}", dto);

        ApkUpload apkUpload = new ApkUpload();
        apkUpload.setFileName(dto.getFileName());
        apkUpload.setFileSize(dto.getFileSize());
        apkUpload.setUploadedAt(dto.getUploadedAt());
        apkUpload.setTestEntityId(dto.getTestEntityId());

        apkRepo.save(apkUpload);
    }

    public void fallbackApkUploaded(ApkUploadDto dto, Throwable t) {
        log.error("Circuit breaker triggered for ApkUploadDto: {}, Error: {}", dto, t.getMessage());
    }

}

