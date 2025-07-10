package com.phone.controller;

import com.phone.dto.PhoneDTO;
import com.phone.service.PhoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    public ResponseEntity<List<PhoneDTO>> getAllPhones() {
        List<PhoneDTO> phoneDTOS = phoneService.getAllPhones();
        return new ResponseEntity<>(phoneDTOS, HttpStatus.OK);
    }

    @Operation(summary = "Update Phone Data from primary source", description = "Update Phone Data from primary source.")
    @PostMapping("/update")
    public ResponseEntity<Void> updatePhones() {
        phoneService.updateFromPrimarySource();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get Phone with {id}", description = "Get Phone with {id}.")
    @ApiResponse(responseCode = "200", description = "Found the phone")
    @ApiResponse(responseCode = "404", description = "Phone not found")
    @GetMapping("/{id}")
    public ResponseEntity<PhoneDTO> getPhoneById(@PathVariable("id") @Min(1) Long id) {
        PhoneDTO phoneDTO = phoneService.getPhoneById(id);
        return new ResponseEntity<>(phoneDTO, HttpStatus.OK);
    }

    @Operation(summary = "Create Phone", description = "Create Phone.")
    @PostMapping("/create")
    public ResponseEntity<PhoneDTO> createPhone(@Valid @RequestBody PhoneDTO phoneDTO) {
        PhoneDTO phone = phoneService.createPhone(phoneDTO);
        return new ResponseEntity<>(phone, HttpStatus.CREATED);
    }
}