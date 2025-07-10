package com.phone.service;

import com.phone.dto.PhoneDTO;
import com.phone.exception.PhoneNotFoundException;
import com.phone.mapper.PhoneMapper;
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
    private final PhoneMapper phoneMapper;

    private List<PhoneDTO> phoneDTOS = new ArrayList<>();

    @Autowired
    public PhoneServiceImpl(PhoneRepository phoneRepository, PhoneMapper phoneMapper) {
        this.phoneRepository = phoneRepository;
        this.phoneMapper = phoneMapper;

        phoneDTOS.add(new PhoneDTO(1L, "iPhone"));
        phoneDTOS.add(new PhoneDTO(2L, "Samsung Galaxy"));
        phoneDTOS.add(new PhoneDTO(3L, "Google Pixel"));
        phoneDTOS.add(new PhoneDTO(4L, "Google Pixel"));
    }

    @Cacheable("phones")
    @Override
    public List<PhoneDTO> getAllPhones() {
        return phoneDTOS.stream()
                .distinct()
                .sorted((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public void updatePhones(List<PhoneDTO> newPhones) {
        phoneDTOS = newPhones;
    }

    @Override
    public void updateFromPrimarySource() {
        List<PhoneDTO> primarySourcePhones = phoneMapper.toDtoList(phoneRepository.findAll());

        updatePhones(primarySourcePhones);
    }

    @Override
    public PhoneDTO getPhoneByIdFromPrimarySource(Long id) {
        return phoneMapper.toDto(phoneRepository.findById(id)
                .orElseThrow(() -> new PhoneNotFoundException("Phone not found with id: " + id)));
    }

    @Override
    public PhoneDTO getPhoneById(Long id) {
        return phoneDTOS.stream()
                .filter(phone -> phone.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new PhoneNotFoundException("Phone not found with id: " + id));
    }

    @Override
    public PhoneDTO createPhone(PhoneDTO phone) {
        return phoneMapper.toDto(phoneRepository.save(phoneMapper.toEntity(phone)));
    }

    @Override
    public void deletePhone(Long id) {
        phoneRepository.deleteById(id);
    }

    @Override
    public PhoneDTO updatePhone(PhoneDTO phone) {
        return phoneMapper.toDto(phoneRepository.save(phoneMapper.toEntity(phone)));
    }

    @Override
    public List<PhoneDTO> filterByName(String name) {
        return phoneDTOS.stream()
                .filter(phone -> phone.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<PhoneDTO> nameContains(String value) {
        return phoneDTOS.stream().filter(p -> p.getName().contains(value)).collect(Collectors.toList());
    }
}