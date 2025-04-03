package com.telekom.phone.service;

import com.telekom.phone.model.Phone;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhoneService {
    private List<Phone> phones = new ArrayList<>();

    public PhoneService() {
        // Inicializace seznamu telefonů
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

    // Method to update data from a primary source
    public void updateFromPrimarySource() {
        List<Phone> primarySourcePhones = List.of(
                new Phone(5L, "OnePlus"),
                new Phone(6L, "Nokia"),
                new Phone(7L, "Sony Xperia"),
                new Phone(8L, "Sony Xperia")
        );
        updatePhones(primarySourcePhones);
    }
}