package com.headstrait.dataconsumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.headstrait.dataconsumer.entity.WaterPortability;
import com.headstrait.dataconsumer.entity.WaterPortabilityModel;
import com.headstrait.dataconsumer.repository.JsonConsumerRepository;
import com.headstrait.dataconsumer.repository.WaterPotabilityRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WaterPotabilityEventService {

    @Autowired
    WaterPotabilityRepository waterPotabilityRepository;

    @Autowired
    JsonConsumerRepository jsonConsumerRepository;

    @Autowired
    ObjectMapper objectMapper;

    private WaterPortability getFromJson(String json, Integer id) {
        JSONObject obj = new JSONObject(json);
        return new WaterPortability(id, obj.get("ph").toString(), obj.get("hardness").toString(), obj.get("solids").toString(),
                obj.get("chloramines").toString(), obj.get("conductivity").toString(), obj.get("organic_carbon").toString(), obj.get("trihalomethanes").toString(),
                obj.get("turbidity").toString(), obj.get("potability").toString());
    }

    public void processConsumerEvent(ConsumerRecord<Integer, String> consumerRecord){
        //WaterPortability consumerEvent = getFromJson(consumerRecord.value(),consumerRecord.key());
        WaterPortability consumerEvent;
        try{
            consumerEvent = objectMapper.readValue(consumerRecord.value(),WaterPortability.class);
            log.info("Consumer Event : {}",consumerEvent);
            int key = consumerRecord.key();
            log.info("Consumer Key : {}",key);

            save(consumerEvent);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }

    public void processJsonConsumerEvent(ConsumerRecord<Integer, WaterPortabilityModel> consumerRecord){
        //WaterPortability consumerEvent = getFromJson(consumerRecord.value(),consumerRecord.key());
        WaterPortabilityModel consumerModel = consumerRecord.value();
        log.info("Consumer Event : {}",consumerModel);
        int key = consumerRecord.key();
        log.info("Consumer Key : {}",key);
        saveEvent(consumerModel);
    }

    private void save(WaterPortability consumerEvent) {
        waterPotabilityRepository.save(consumerEvent);
        log.info("Consumer Event: {} Persisted!",consumerEvent);
    }

    private void saveEvent(WaterPortabilityModel waterPortabilityModel) {
        jsonConsumerRepository.save(waterPortabilityModel);
        log.info("Consumer Event: {} Persisted!",waterPortabilityModel);
    }


}
