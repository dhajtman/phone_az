package com.telekom.phone.controller;

import com.telekom.phone.model.Phone;
import com.telekom.phone.service.PhoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/phone")
public class PhoneController {
    private final PhoneService phoneService;

    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Phone>> getAllPhones() {
        List<Phone> phones = phoneService.getAllPhones();
        return new ResponseEntity<>(phones, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updatePhones() {
        phoneService.updateFromPrimarySource();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}