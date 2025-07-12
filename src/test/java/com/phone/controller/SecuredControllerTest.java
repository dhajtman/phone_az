package com.phone.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SecuredController.class)
public class SecuredControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAccessWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/api/v1/private"))
                .andExpect(status().isUnauthorized()); // Expect 401 Unauthorized
    }

}
