package com.telekom.phone.service;

import com.telekom.phone.model.Phone;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PhoneServiceTest {

    @Test
    public void testGetAllPhones() {
        PhoneService phoneService = new PhoneService();
        List<Phone> phones = phoneService.getAllPhones();

        assertEquals(3, phones.size());
        assertEquals("Google Pixel", phones.get(0).getName());
        assertEquals("iPhone", phones.get(1).getName());
        assertEquals("Samsung Galaxy", phones.get(2).getName());
    }

    @Test
    public void testUpdateFromPrimarySource() {
        PhoneService phoneService = new PhoneService();
        phoneService.updateFromPrimarySource();
        List<Phone> phones = phoneService.getAllPhones();

        assertEquals(3, phones.size());
        assertEquals("Nokia", phones.get(0).getName());
        assertEquals("OnePlus", phones.get(1).getName());
        assertEquals("Sony Xperia", phones.get(2).getName());
    }

    @Test
    public void testUpdatePhones() {
        PhoneService phoneService = new PhoneService();
        List<Phone> newPhones = List.of(
                new Phone(7L, "Motorola"),
                new Phone(8L, "Huawei"),
                new Phone(9L, "Xiaomi")
        );
        phoneService.updatePhones(newPhones);
        List<Phone> phones = phoneService.getAllPhones();

        assertEquals(3, phones.size());
        assertEquals("Huawei", phones.get(0).getName());
        assertEquals("Motorola", phones.get(1).getName());
        assertEquals("Xiaomi", phones.get(2).getName());
    }
}