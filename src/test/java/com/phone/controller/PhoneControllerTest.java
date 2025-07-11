package com.phone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phone.dto.PhoneDTO;
import com.phone.model.Phone;
import com.phone.service.PhoneService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext
public class PhoneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        phoneService.updatePhones(List.of(
                new PhoneDTO(1L, "iPhone"),
                new PhoneDTO(2L, "Samsung Galaxy"),
                new PhoneDTO(3L, "Google Pixel"),
                new PhoneDTO(4L, "Google Pixel")
        ));
    }

    @Test
    public void testGetAllPhones() throws Exception {
        mockMvc.perform(get("/api/v1/public/phone/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':3,'name':'Google Pixel'},{'id':1,'name':'iPhone'},{'id':2,'name':'Samsung Galaxy'}]"));
    }

    @Test
    @DirtiesContext
    public void testUpdatePhones() throws Exception {
        mockMvc.perform(post("/api/v1/public/phone/update")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/public/phone/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":3,\"name\":\"Google Pixel\"},{\"id\":1,\"name\":\"iPhone\"},{\"id\":6,\"name\":\"Nokia\"},{\"id\":5,\"name\":\"OnePlus\"},{\"id\":2,\"name\":\"Samsung Galaxy\"},{\"id\":7,\"name\":\"Sony Xperia\"}]"));
    }

    @Test
    @DirtiesContext
    public void testCreatePhone() throws Exception {
        PhoneDTO phoneDTO = PhoneDTO.builder().name("OnePlusTest").build();

        ResultActions resultActions = mockMvc.perform(post("/api/v1/public/phone/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(phoneDTO)))
                .andExpect(status().isCreated());
        Phone createdPhoneEntity = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), Phone.class);
        Assertions.assertEquals(phoneDTO.getName(), createdPhoneEntity.getName());
    }

    @Test
    @DirtiesContext
    public void testCreatePhoneNotValid() throws Exception {
        PhoneDTO phoneDTO = PhoneDTO.builder().name("x").build();

        ResultActions resultActions = mockMvc.perform(post("/api/v1/public/phone/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(phoneDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"name\":\"Name must be between 2 and 50 characters\"}"));
    }
}