package com.phone.service;

import com.phone.exception.PhoneNotFoundException;
import com.phone.model.Phone;
import com.phone.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhoneServiceImpl implements PhoneService {
    private final PhoneRepository phoneRepository;

    private List<Phone> phones = new ArrayList<>();

    @Autowired
    public PhoneServiceImpl(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;

        phones.add(new Phone(1L, "iPhone"));
        phones.add(new Phone(2L, "Samsung Galaxy"));
        phones.add(new Phone(3L, "Google Pixel"));
        phones.add(new Phone(4L, "Google Pixel"));
    }

    @Cacheable("phones")
    @Override
    public List<Phone> getAllPhones() {
        return phones.stream()
                .distinct()
                .sorted((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public void updatePhones(List<Phone> newPhones) {
        phones = newPhones;
    }

    @Override
    public void updateFromPrimarySource() {
        List<Phone> primarySourcePhones = phoneRepository.findAll();

        updatePhones(primarySourcePhones);
    }

    @Override
    public Phone getPhoneByIdFromPrimarySource(Long id) {
        return phoneRepository.findById(id)
                .orElseThrow(() -> new PhoneNotFoundException("Phone not found with id: " + id));
    }

    @Override
    public Phone getPhoneById(Long id) {
        return phones.stream()
                .filter(phone -> phone.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new PhoneNotFoundException("Phone not found with id: " + id));
    }

    @Override
    public Phone createPhone(Phone phone) {
        return phoneRepository.save(phone);
    }

    @Override
    public void deletePhone(Long id) {
        phoneRepository.deleteById(id);
    }

    @Override
    public Phone updatePhone(Phone phone) {
        return phoneRepository.save(phone);
    }

    @Override
    public List<Phone> filterByName(String name) {
        return phones.stream()
                .filter(phone -> phone.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<Phone> nameContains(String value) {
        return phones.stream().filter(p -> p.getName().contains(value)).collect(Collectors.toList());
    }
}