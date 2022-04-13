package com.headstrait.lookuptest.service;

import com.headstrait.lookup.entity.WaterPortabilityModel;
import com.headstrait.lookup.repository.WaterPotabilityRepository;
import com.headstrait.lookup.service.LookUpService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.headstrait.lookuptest.constants.TestObjects.WATER_PORTABILITY_MODEL;
import static com.headstrait.lookuptest.constants.TestObjects.WATER_PORTABILITY_MODELS;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LookUpServiceTest {

    @Mock
    WaterPotabilityRepository waterPotabilityRepository;

    @InjectMocks
    LookUpService lookUpService;

    @Test
    public void getAll_Should_FetchAllWaterPotabilityData(){
        when(waterPotabilityRepository.findAll())
                .thenReturn(WATER_PORTABILITY_MODELS);
        List<WaterPortabilityModel> actualList = lookUpService.getAll();
        List<WaterPortabilityModel> expectedList = WATER_PORTABILITY_MODELS;
        Assert.assertNotNull(actualList);
        Assertions.assertEquals(1,actualList.size());
        Assertions.assertEquals(expectedList,actualList);
    }

    @Test
    public void get_Should_FetchWaterPotabilityDataOfProvidedId(){
        when(waterPotabilityRepository.findById(1))
                .thenReturn(WATER_PORTABILITY_MODEL);
        WaterPortabilityModel actualData = lookUpService.get(1);
        WaterPortabilityModel expectedData = WATER_PORTABILITY_MODEL;
        Assert.assertNotNull(actualData);
        Assertions.assertEquals(expectedData,actualData);
    }
}
