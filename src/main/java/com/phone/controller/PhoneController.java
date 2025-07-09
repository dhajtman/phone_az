package com.phone.controller;

import com.phone.model.Phone;
import com.phone.service.PhoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phone")
@Validated
@Tag(name = "Phone", description = "This API provides the capability to Phone from a Phone Repository")
public class PhoneController {
    private final PhoneService phoneService;

    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @Operation(summary = "Get All Phone Data", description = "Get All Phone Data.")
    @GetMapping("/all")
    public ResponseEntity<List<Phone>> getAllPhones() {
        List<Phone> phones = phoneService.getAllPhones();
        return new ResponseEntity<>(phones, HttpStatus.OK);
    }

    @Operation(summary = "Update Phone Data from primary source", description = "Update Phone Data from primary source.")
    @PostMapping("/update")
    public ResponseEntity<Void> updatePhones() {
        phoneService.updateFromPrimarySource();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get Phone with {id}", description = "Get Phone with {id}.")
    @GetMapping("/{id}")
    public ResponseEntity<Phone> getPhoneById(@PathVariable("id") @Min(1) Long id) {
        Phone phone = phoneService.getPhoneById(id);
        return new ResponseEntity<>(phone, HttpStatus.OK);
    }

    @Operation(summary = "Create Phone", description = "Create Phone.")
    @PostMapping("/create")
    public ResponseEntity<Phone> createPhone(@Valid @RequestBody Phone phone) {
        Phone createdPhone = phoneService.createPhone(phone);
        return new ResponseEntity<>(createdPhone, HttpStatus.CREATED);
    }
}