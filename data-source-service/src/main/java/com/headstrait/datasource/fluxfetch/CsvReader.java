package com.headstrait.datasource.fluxfetch;

import lombok.extern.slf4j.Slf4j;
import com.headstrait.datasource.constants.Constants;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.headstrait.datasource.utils.CommonUtils.startTimer;
import static com.headstrait.datasource.utils.CommonUtils.timeTaken;

@Slf4j
@Component
public class CsvReader {

    static final Path ipPath = Paths.get(Constants.filePath);

    public static Mono<String> stringMono;

    static {
        try {
            System.out.println(ipPath.toAbsolutePath());
            stringMono = Mono.just(csvToJson(Files.lines(ipPath).collect(Collectors.toList())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String csvToJson(List<String> csv){

        startTimer();

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

        timeTaken();
        return json.toString();
    }

}