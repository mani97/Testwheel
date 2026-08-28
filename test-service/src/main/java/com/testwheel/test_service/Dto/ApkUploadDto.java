package com.testwheel.test_service.Dto;

import com.testwheel.test_service.model.TestEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApkUploadDto {

        private Long id;

        private String fileName;
        private String filePath;
        private long fileSize;
        private LocalDateTime uploadedAt;

        private String packageName;
        private Long versionCode;
        private String versionName;
        private String permissions;

        private Long testEntityId;
    }
