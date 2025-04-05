package com.telekom.phone.service;

import com.telekom.phone.exception.PhoneNotFoundException;
import com.telekom.phone.model.Phone;
import com.telekom.phone.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhoneService {
    private final PhoneRepository phoneRepository;

    private List<Phone> phones = new ArrayList<>();

    @Autowired
    public PhoneService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;

        phones.add(new Phone(1L, "iPhone"));
        phones.add(new Phone(2L, "Samsung Galaxy"));
        phones.add(new Phone(3L, "Google Pixel"));
        phones.add(new Phone(4L, "Google Pixel"));
    }

    public List<Phone> getAllPhones() {
        return phones.stream()
                .distinct()
                .sorted((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()))
                .collect(Collectors.toList());
    }

    public void updatePhones(List<Phone> newPhones) {
        phones = newPhones;
    }

    public void updateFromPrimarySource() {
        List<Phone> primarySourcePhones = phoneRepository.findAll();

        updatePhones(primarySourcePhones);
    }

    public Phone getPhoneByIdFromPrimarySource(Long id) {
        return phoneRepository.findById(id)
                .orElseThrow(() -> new PhoneNotFoundException("Phone not found with id: " + id));
    }

    public Phone getPhoneById(Long id) {
        return phones.stream()
                .filter(phone -> phone.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new PhoneNotFoundException("Phone not found with id: " + id));
    }
}