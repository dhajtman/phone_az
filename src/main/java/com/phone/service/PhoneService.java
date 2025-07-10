package com.phone.service;

import com.phone.dto.PhoneDTO;

import java.util.List;

public interface PhoneService {

    List<PhoneDTO> getAllPhones();

    void updatePhones(List<PhoneDTO> newPhones);

    void updateFromPrimarySource();

    PhoneDTO getPhoneByIdFromPrimarySource(Long id);

    PhoneDTO getPhoneById(Long id);

    PhoneDTO createPhone(PhoneDTO phone);

    void deletePhone(Long id);

    PhoneDTO updatePhone(PhoneDTO phone);

    List<PhoneDTO> filterByName(String name);

    List<PhoneDTO> nameContains(String value);
}
