package com.phone.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/private")
public class SecuredController {

    @GetMapping("/hello")
    public String hello(Principal principal) {
        return "Hello, " + principal.getName();
    }
}
