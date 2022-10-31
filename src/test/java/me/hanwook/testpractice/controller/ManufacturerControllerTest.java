package me.hanwook.testpractice.controller;

import me.hanwook.testpractice.entity.Manufacturer;
import me.hanwook.testpractice.service.ManufacturerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ManufacturerController.class)
class ManufacturerControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ManufacturerService manufacturerService;

    @Test
    void 제조사_등록_테스트() throws Exception {
        // given
        String name = "테스트";
        String json = "{\"name\": \""+name+"\"}";

        when(manufacturerService.create(name))
                .thenReturn(
                        Manufacturer.builder()
                                .build()
                );

        // when & then
        mvc.perform(post("/apis/v1/manufacturers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"));
    }
}