package com.example.munroapi.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.example.munroapi.models.Munro;
import com.example.munroapi.services.MunroService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    private MunroService munroService;


    Munro mun1;
    Munro mun2;
    Munro mun3;
    Munro mun4;
    Munro mun5;

    @BeforeEach
    public void before(){
        mun1 = new Munro("Big Hill", 1000.2f,"NN12345", "MUN");
        mun2 = new Munro("Oor Hill", 500.2f,"NN12346", "TOP");
        mun3 = new Munro("Your Hill", 1300.f,"NN12347", "MUN");
        mun4 = new Munro("Everybuddys Hill", 500.4f,"NN12348", "TOP");
        mun5 = new Munro("Little Hill", 980.3f,"NN12349", "MUN");

        List<Munro> munros = List.of(mun1, mun2, mun3, mun4, mun5);
        munroService.setMunros(munros);

    }

    @Test
    public void shouldReturnAllExpectedMunros() throws Exception {
        // Given there are some munros

        // When we get all munros
        this.mockMvc.perform(get("/munros"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].name", is(mun1.getName())))
                .andExpect(jsonPath("$[1].name", is(mun2.getName())))
                .andExpect(jsonPath("$[2].name", is(mun3.getName())))
                .andExpect(jsonPath("$[3].name", is(mun4.getName())))
                .andExpect(jsonPath("$[4].name", is(mun5.getName())));
    }

} 
