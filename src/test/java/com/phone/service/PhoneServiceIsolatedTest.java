package com.phone.service;

import com.phone.dto.PhoneDTO;
import com.phone.mapper.PhoneMapper;
import com.phone.model.Phone;
import com.phone.repository.PhoneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PhoneServiceIsolatedTest {
    @Mock
    private PhoneRepository phoneRepository;

    @Mock
    private PhoneMapper phoneMapper;

    @InjectMocks
    private PhoneServiceImpl phoneService;

    @Test
    void shouldReturnPhoneById() {
        String phoneName = "Nokia";
        Phone phone = new Phone(1L, phoneName);
        when(phoneRepository.findById(1L)).thenReturn(Optional.of(phone));

        PhoneDTO dto = new PhoneDTO(1L, phoneName);
        when(phoneMapper.toDto(phone)).thenReturn(dto);

        PhoneDTO result = phoneService.getPhoneByIdFromPrimarySource(1L);

        assertEquals(phoneName, result.getName());
    }
}
