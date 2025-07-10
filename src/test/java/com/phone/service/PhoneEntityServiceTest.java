package com.phone.service;

import com.phone.dto.PhoneDTO;
import com.phone.exception.PhoneNotFoundException;
import com.phone.mapper.PhoneMapper;
import com.phone.model.Phone;
import com.phone.repository.PhoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PhoneEntityServiceTest {
    @Mock
    private PhoneRepository phoneRepository;

    @Autowired
    private PhoneMapper phoneMapper;

    private PhoneService phoneService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        phoneService = new PhoneServiceImpl(phoneRepository, phoneMapper);
    }

    @Test
    public void testGetAllPhones() {
        List<PhoneDTO> phoneEntities = phoneService.getAllPhones();

        assertEquals(3, phoneEntities.size());
        assertEquals("Google Pixel", phoneEntities.get(0).getName());
        assertEquals("iPhone", phoneEntities.get(1).getName());
        assertEquals("Samsung Galaxy", phoneEntities.get(2).getName());
    }

    @Test
    public void testUpdateFromPrimarySourceMockRepositoryData() {
        List<Phone> primarySourcePhoneEntities = List.of(
                new Phone(5L, "OnePlus"),
                new Phone(6L, "Nokia"),
                new Phone(7L, "Sony Xperia"),
                new Phone(8L, "Sony Xperia")
        );
        when(phoneRepository.findAll()).thenReturn(primarySourcePhoneEntities);
        
        phoneService.updateFromPrimarySource();
        List<PhoneDTO> phoneDTOS = phoneService.getAllPhones();

        assertEquals(3, phoneDTOS.size());
        assertEquals("Nokia", phoneDTOS.get(0).getName());
        assertEquals("OnePlus", phoneDTOS.get(1).getName());
        assertEquals("Sony Xperia", phoneDTOS.get(2).getName());
    }

    @Test
    public void testUpdatePhones() {
        List<PhoneDTO> newPhoneEntities = List.of(
                new PhoneDTO(7L, "Motorola"),
                new PhoneDTO(8L, "Huawei"),
                new PhoneDTO(9L, "Xiaomi"),
                new PhoneDTO(10L, "Xiaomi")
        );
        phoneService.updatePhones(newPhoneEntities);
        List<PhoneDTO> phoneDTOS = phoneService.getAllPhones();

        assertEquals(3, phoneDTOS.size());
        assertEquals("Huawei", phoneDTOS.get(0).getName());
        assertEquals("Motorola", phoneDTOS.get(1).getName());
        assertEquals("Xiaomi", phoneDTOS.get(2).getName());
    }

    @Test
    public void testGetPhoneById() {
        Phone phoneEntity = new Phone(1L, "iPhone");
        when(phoneRepository.findById(1L)).thenReturn(Optional.of(phoneEntity));

        PhoneDTO result = phoneService.getPhoneById(1L);

        assertEquals(1L, result.getId());
        assertEquals("iPhone", result.getName());
    }

    @Test
    public void testGetPhoneByIdNotFound() {
        when(phoneRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(PhoneNotFoundException.class, () -> phoneService.getPhoneById(5L));
    }
}