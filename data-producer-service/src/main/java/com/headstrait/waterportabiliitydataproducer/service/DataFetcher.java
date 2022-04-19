package com.headstrait.waterportabiliitydataproducer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.headstrait.waterportabiliitydataproducer.model.WaterPortability;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DataFetcher {

    @Autowired
    ObjectMapper objectMapper;

    public List<WaterPortability> fetchData(){

        List<WaterPortability> listOfEventsToBeSentToTopic = new ArrayList<>();
        try {
            List<LinkedHashMap<String,String>> hashMaps =
                    objectMapper.readValue(new URL("http://localhost:9001/get"),
                            List.class);
            listOfEventsToBeSentToTopic = hashMaps.stream().map(
                    hashMap -> new WaterPortability(hashMap.get("ph"), hashMap.get("Hardness"), hashMap.get("Solids"),
                            hashMap.get("Chloramines"), hashMap.get("Conductivity"), hashMap.get("Organic_carbon"),
                            hashMap.get("Trihalomethanes"), hashMap.get("Turbidity"), hashMap.get("Potability"))
            ).collect(Collectors.toList());
        } catch (IOException e) {
            log.error(e.getMessage());
//            e.printStackTrace();
        }
        return listOfEventsToBeSentToTopic;
    }
}
