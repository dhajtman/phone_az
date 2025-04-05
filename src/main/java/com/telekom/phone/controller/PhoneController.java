package com.telekom.phone.controller;

import com.telekom.phone.model.Phone;
import com.telekom.phone.service.PhoneService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<Phone> getPhoneById(@PathVariable("id") Long id) {
        Phone phone = phoneService.getPhoneById(id);
        return new ResponseEntity<>(phone, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Phone> createPhone(@Valid @RequestBody Phone phone) {
        Phone createdPhone = phoneService.createPhone(phone);
        return new ResponseEntity<>(createdPhone, HttpStatus.CREATED);
    }
}