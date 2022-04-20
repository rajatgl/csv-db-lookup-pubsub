package com.headstrait.lookuptest.controller;

import com.headstrait.lookup.controller.LookUpController;
import com.headstrait.lookup.service.LookUpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.headstrait.lookuptest.constants.TestObjects.WATER_PORTABILITY_MODEL;
import static com.headstrait.lookuptest.constants.TestObjects.WATER_PORTABILITY_MODELS;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class LookUpControllerTest {
    @Mock
    LookUpService lookUpService;

    @InjectMocks
    LookUpController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void getRequest_toFetchEndpointOfController_FetchesAvailableDataAsAResponse()
            throws Exception {
        when(lookUpService.getAll())
                .thenReturn(WATER_PORTABILITY_MODELS);

        mockMvc.perform(MockMvcRequestBuilders.get("/fetch"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].ph", is(1.0)))
                .andExpect(jsonPath("$.[0].hardness", is(1.0)))
                .andExpect(jsonPath("$.[0].solids", is(1.0)))
                .andExpect(jsonPath("$.[0].chloramines", is(1.0)))
                .andExpect(jsonPath("$.[0].conductivity", is(1.0)))
                .andExpect(jsonPath("$.[0].organic_carbon", is(1.0)))
                .andExpect(jsonPath("$.[0].trihalomethanes", is(1.0)))
                .andExpect(jsonPath("$.[0].turbidity", is(1.0)))
                .andExpect(jsonPath("$.[0].potability", is(1.0)));
    }


    @Test
    public void getRequest_toFetchByIdEndpointOfController_FetchesAvailableDataAsAResponse()
            throws Exception {
        when(lookUpService.get(1))
                .thenReturn(WATER_PORTABILITY_MODEL);

        mockMvc.perform(MockMvcRequestBuilders.get("/fetch/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.ph", is(1.0)))
                .andExpect(jsonPath("$.hardness", is(1.0)))
                .andExpect(jsonPath("$.solids", is(1.0)))
                .andExpect(jsonPath("$.chloramines", is(1.0)))
                .andExpect(jsonPath("$.conductivity", is(1.0)))
                .andExpect(jsonPath("$.organic_carbon", is(1.0)))
                .andExpect(jsonPath("$.trihalomethanes", is(1.0)))
                .andExpect(jsonPath("$.turbidity", is(1.0)))
                .andExpect(jsonPath("$.potability", is(1.0)));
    }

}
