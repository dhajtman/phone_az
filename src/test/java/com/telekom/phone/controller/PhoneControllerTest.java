package com.telekom.phone.controller;

import com.telekom.phone.model.Phone;
import com.telekom.phone.service.PhoneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PhoneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PhoneService phoneService;

    @BeforeEach
    public void setUp() {
        phoneService.updatePhones(List.of(
                new Phone(1L, "iPhone"),
                new Phone(2L, "Samsung Galaxy"),
                new Phone(3L, "Google Pixel"),
                new Phone(4L, "Google Pixel")
        ));
    }

    @Test
    public void testGetAllPhones() throws Exception {
        mockMvc.perform(get("/phone/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':3,'name':'Google Pixel'},{'id':1,'name':'iPhone'},{'id':2,'name':'Samsung Galaxy'}]"));
    }

    @Test
    public void testUpdatePhones() throws Exception {
        mockMvc.perform(post("/phone/update")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/phone/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':6,'name':'Nokia'},{'id':5,'name':'OnePlus'},{'id':7,'name':'Sony Xperia'}]"));
    }
}