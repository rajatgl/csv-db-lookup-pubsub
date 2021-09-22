package com.headstrait.lookup.service;

import com.headstrait.lookup.entity.WaterPortabilityModel;
import com.headstrait.lookup.repository.WaterPotabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LookUpService {
    @Autowired
    private WaterPotabilityRepository waterPotabilityRepository;

    public List<WaterPortabilityModel> getAll(){
        return waterPotabilityRepository.findAll();
    }

    public WaterPortabilityModel get(int id){
        return waterPotabilityRepository.findById(id);
    }
}
