package com.example.munroapi;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class MunroControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @Test
	public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/munros"))
            .andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.message", is("No data found. Please post a csv to /munros/upload as form-data under the key 'file'.")))
            .andExpect(jsonPath("$.status", is(404)));
	}
} 
