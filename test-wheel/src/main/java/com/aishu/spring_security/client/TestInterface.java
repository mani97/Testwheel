package com.aishu.spring_security.client;

import com.aishu.spring_security.Dto.ApkUploadDto;
import com.aishu.spring_security.Dto.TestEntityDto;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "test-service")
public interface TestInterface {

//    @PostMapping("/createtest")
//    TestEntityDto createTest(@RequestBody TestEntityDto dto);
//
//    @PostMapping(value = "/uploadApk", consumes = "multipart/form-data")
//    ApkUploadDto uploadApk(@RequestPart("file") MultipartFile apkFile,
//                           @RequestParam("testId") int testId);

    @GetMapping("testrequest/count")
    long getTestCount();

}
