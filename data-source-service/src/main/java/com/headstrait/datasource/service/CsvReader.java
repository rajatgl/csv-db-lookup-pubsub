package com.headstrait.datasource.service;

import com.headstrait.datasource.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CsvReader {

    static final Path filePath = Paths.get(Constants.filePath);

    private Mono<String> stringMono;

    public String getStringFromMono() {
        try {
            System.out.println(filePath.toAbsolutePath());
            stringMono = Mono.just(csvToJson(Files.lines(filePath)
                    .collect(Collectors.toList())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringMono.block();
    }

    private String csvToJson(List<String> csv){
        //remove empty lines
        csv.removeIf(e -> e.trim().isEmpty());

        //csv is empty or have declared only columns
        if(csv.size() <= 1){
            return "[]";
        }

        //get first line = columns names
        String[] columns = csv.get(0).split(",");

        //get all rows
        StringBuilder json = new StringBuilder("[\n");
        csv.subList(1, csv.size()) //substring without first row(columns)
                .stream()
                .map(e -> e.split(","))
                .filter(e -> e.length == columns.length) //values size should match with columns size
                .forEach(row -> {

                    json.append("\t{\n");

                    for(int i = 0; i < columns.length; i++){
                        json.append("\t\t\"")
                                .append(columns[i])
                                .append("\" : \"")
                                .append(row[i])
                                .append("\",\n"); //comma-1
                    }

                    //replace comma-1 with \n
                    json.replace(json.lastIndexOf(","), json.length(), "\n");

                    json.append("\t},"); //comma-2

                });

        //remove comma-2
        json.replace(json.lastIndexOf(","), json.length(), "");

        json.append("\n]");

        return json.toString();
    }

}