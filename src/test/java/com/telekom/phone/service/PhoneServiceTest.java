package com.telekom.phone.service;

import com.telekom.phone.model.Phone;
import com.telekom.phone.repository.PhoneRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PhoneServiceTest {
    @Mock
    private PhoneRepository phoneRepository;

    @InjectMocks
    private PhoneService phoneService;

    @Test
    public void testGetAllPhones() {
        List<Phone> phones = phoneService.getAllPhones();

        assertEquals(3, phones.size());
        assertEquals("Google Pixel", phones.get(0).getName());
        assertEquals("iPhone", phones.get(1).getName());
        assertEquals("Samsung Galaxy", phones.get(2).getName());
    }

    @Test
    public void testUpdateFromPrimarySourceMockRepositoryData() {
        List<Phone> primarySourcePhones = List.of(
                new Phone(5L, "OnePlus"),
                new Phone(6L, "Nokia"),
                new Phone(7L, "Sony Xperia"),
                new Phone(8L, "Sony Xperia")
        );
        when(phoneRepository.findAll()).thenReturn(primarySourcePhones);

        phoneService.updateFromPrimarySource();
        List<Phone> phones = phoneService.getAllPhones();

        assertEquals(3, phones.size());
        assertEquals("Nokia", phones.get(0).getName());
        assertEquals("OnePlus", phones.get(1).getName());
        assertEquals("Sony Xperia", phones.get(2).getName());
    }

    @Test
    public void testUpdatePhones() {
        List<Phone> newPhones = List.of(
                new Phone(7L, "Motorola"),
                new Phone(8L, "Huawei"),
                new Phone(9L, "Xiaomi"),
                new Phone(10L, "Xiaomi")
        );
        phoneService.updatePhones(newPhones);
        List<Phone> phones = phoneService.getAllPhones();

        assertEquals(3, phones.size());
        assertEquals("Huawei", phones.get(0).getName());
        assertEquals("Motorola", phones.get(1).getName());
        assertEquals("Xiaomi", phones.get(2).getName());
    }
}