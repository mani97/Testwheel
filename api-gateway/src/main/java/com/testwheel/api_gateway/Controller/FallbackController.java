package com.testwheel.api_gateway.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/wheel")
    public ResponseEntity<String> wheelFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("TestWheel Service is currently unavailable. Please try again later.");
    }

    @GetMapping("/test")
    public ResponseEntity<String> testFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Test Service is currently unavailable. Please try again later.");
    }
}
