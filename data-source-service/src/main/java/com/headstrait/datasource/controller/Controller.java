package com.headstrait.datasource.controller;

import com.headstrait.datasource.fluxfetch.CsvReader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class Controller {

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE )
    public static Mono<String> getData(){
        return CsvReader.stringMono;
    }
}
