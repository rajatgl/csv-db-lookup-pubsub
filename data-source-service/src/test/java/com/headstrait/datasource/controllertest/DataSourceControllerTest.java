package com.headstrait.datasource.controllertest;


import com.headstrait.datasource.controller.DataSourceController;
import com.headstrait.datasource.service.CsvReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
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
    DataSourceController dataSourceController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(dataSourceController)
                .build();
    }

    @Test
    public void getRequest_toGetEndpointOfController_FetchesAvailableDataFromCSVFileAsAResponse()
            throws Exception {
        when(csvReader.getStringFromMono())
                .thenReturn(JSON_STRING);

        mockMvc.perform(MockMvcRequestBuilders.get("/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].ph", is("0.0")))
                .andExpect(jsonPath("$.[0].hardness", is("0.0")));
    }

}
