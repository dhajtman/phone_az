package com.phone.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/private")
@Slf4j
public class SecuredController {

    @GetMapping("/hello")
    public ResponseEntity<Map<String, String>> hello(Principal principal) {
        Map<String, String> response = Map.of("message", "Hello, " + principal.getName());
        log.info("Accessed secured endpoint by user: {}", principal.getName());
        return ResponseEntity.ok(response);
    }
}
