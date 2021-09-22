package com.headstrait.lookup.controller;


import com.headstrait.lookup.entity.WaterPortabilityModel;
import com.headstrait.lookup.service.LookUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LookUpController {

    @Autowired
    private LookUpService lookUpService;

    @GetMapping("/fetch")
    List<WaterPortabilityModel> getAll(){
        return lookUpService.getAll();
    }

    @GetMapping("/fetch/{id}")
    WaterPortabilityModel get(@PathVariable(value = "id") int id){
        return lookUpService.get(id);
    }

}
