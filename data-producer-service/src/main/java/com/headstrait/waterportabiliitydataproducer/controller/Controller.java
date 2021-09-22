package com.headstrait.waterportabiliitydataproducer.controller;

import com.headstrait.waterportabiliitydataproducer.producer.WaterPortabilityEventProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class Controller {

    @Autowired
    WaterPortabilityEventProducer waterPortabilityEventProducer;


    @PostMapping("/water-portability")
    public ResponseEntity<String> postLibraryEvent() throws JsonProcessingException, ExecutionException, InterruptedException {

        //invoke kafka producer
        waterPortabilityEventProducer.producer();
        return ResponseEntity.status(HttpStatus.CREATED).body("SUCCESS");
    }
}
