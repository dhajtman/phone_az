package com.phone.exception;

import com.phone.controller.PhoneController;
import com.phone.service.PhoneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PhoneController.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PhoneService phoneService;

    @Test
    @WithMockUser
    public void testHandlePhoneNotFoundException() throws Exception {
        when(phoneService.getPhoneById(1L)).thenThrow(new PhoneNotFoundException("Phone not found with id: 1"));

        mockMvc.perform(get("/phone/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Phone not found"))
                .andExpect(jsonPath("$.message").value("Phone not found with id: 1"));
    }

    @Test
    @WithMockUser
    public void testHandleRuntimeException() throws Exception {
        when(phoneService.getPhoneById(1L)).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/phone/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Runtime exception"))
                .andExpect(jsonPath("$.message").value("Unexpected error"));
    }
}