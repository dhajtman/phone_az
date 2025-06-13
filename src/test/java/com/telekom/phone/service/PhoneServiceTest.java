package com.telekom.phone.service;

import com.telekom.phone.exception.PhoneNotFoundException;
import com.telekom.phone.model.Phone;
import com.telekom.phone.repository.PhoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PhoneServiceTest {
    @Mock
    private PhoneRepository phoneRepository;

    private PhoneService phoneService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        phoneService = new PhoneServiceImpl(phoneRepository); // Use the concrete implementation
    }

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

    @Test
    public void testGetPhoneById() {
        Phone phone = new Phone(1L, "iPhone");
        when(phoneRepository.findById(1L)).thenReturn(Optional.of(phone));

        Phone result = phoneService.getPhoneById(1L);

        assertEquals(1L, result.getId());
        assertEquals("iPhone", result.getName());
    }

    @Test
    public void testGetPhoneByIdNotFound() {
        when(phoneRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(PhoneNotFoundException.class, () -> phoneService.getPhoneById(5L));
    }
}