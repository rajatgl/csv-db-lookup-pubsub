package com.headstrait.datasource.controllertest;


import com.headstrait.datasource.controller.DataSourceController;
import com.headstrait.datasource.service.CsvReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.headstrait.datasource.constants.TestObjects.JSON_STRING;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class DataSourceControllerTest {

    @Mock
    CsvReader csvReader;

    @InjectMocks
    DataSourceController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void getRequest_toGetEndpointOfController_FetchesAvailableDataFromCSVFileAsAResponse()
            throws Exception {

        when(csvReader.getStringFromMono())
                .thenReturn(JSON_STRING);

        mockMvc.perform(MockMvcRequestBuilders.get("/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].ph", is("3.71608007538699")))
                .andExpect(jsonPath("$.[0].Hardness", is("129.42292051494425")))
                .andExpect(jsonPath("$.[0].Solids", is("18630.057857970347")))
                .andExpect(jsonPath("$.[0].Chloramines", is("6.635245883862")))
                .andExpect(jsonPath("$.[0].Sulfate", is("")))
                .andExpect(jsonPath("$.[0].Conductivity", is("592.8853591348523")))
                .andExpect(jsonPath("$.[0].Organic_carbon", is("15.180013116357259")))
                .andExpect(jsonPath("$.[0].Trihalomethanes", is("56.32907628451764")))
                .andExpect(jsonPath("$.[0].Turbidity", is("4.500656274942408")))
                .andExpect(jsonPath("$.[0].Potability", is("0")));
    }

}
