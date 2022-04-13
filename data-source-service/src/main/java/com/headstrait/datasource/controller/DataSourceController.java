package com.headstrait.datasource.controller;

import com.headstrait.datasource.service.CsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataSourceController {

    @Autowired
    CsvReader csvReader;

    @GetMapping(value = "/get",
            produces = MediaType.APPLICATION_JSON_VALUE )
    public String getData(){
        return csvReader.getStringFromMono();
    }
}
