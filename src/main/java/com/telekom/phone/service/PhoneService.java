package com.telekom.phone.service;

import com.telekom.phone.model.Phone;

import java.util.List;

public interface PhoneService {

    List<Phone> getAllPhones();

    void updatePhones(List<Phone> newPhones);

    void updateFromPrimarySource();

    Phone getPhoneByIdFromPrimarySource(Long id);

    Phone getPhoneById(Long id);

    Phone createPhone(Phone phone);

    void deletePhone(Long id);

    Phone updatePhone(Phone phone);

    List<Phone> filterByName(String name);

    List<Phone> nameContains(String value);
}
