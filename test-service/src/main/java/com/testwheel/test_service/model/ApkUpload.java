package com.testwheel.test_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "apk_upload")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApkUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String filePath;
    private long fileSize;
    private LocalDateTime uploadedAt;

    private String packageName;
    private Long versionCode;
    private String versionName;

    @Column(length = 20000)
    private String permissions;


    private Long testEntityId;
}
