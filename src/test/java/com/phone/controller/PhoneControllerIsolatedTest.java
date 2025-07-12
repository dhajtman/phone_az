package com.phone.controller;

import com.phone.dto.PhoneDTO;
import com.phone.mapper.PhoneMapper;
import com.phone.service.PhoneService;
import com.phone.service.PhoneServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PhoneController.class)
public class PhoneControllerIsolatedTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PhoneServiceImpl phoneService;

    @Test
    @WithMockUser
    void shouldReturnPhoneById() throws Exception {
        String phoneName = "iPhone";

        PhoneDTO phone = new PhoneDTO(1L, phoneName);
        when(phoneService.getPhoneById(1L)).thenReturn(phone);

        mockMvc.perform(get("/api/v1/public/phone/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(phoneName));
    }
}
