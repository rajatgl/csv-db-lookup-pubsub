package com.headstrait.dataconsumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.headstrait.dataconsumer.entity.WaterPortabilityModel;
import com.headstrait.dataconsumer.repository.WaterPortabilityRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WaterPotabilityEventService {

    @Autowired
    WaterPortabilityRepository waterPortabilityRepository;

    @Autowired
    ObjectMapper objectMapper;

    public void processJsonConsumerEvent(ConsumerRecord<Integer, WaterPortabilityModel> consumerRecord){
        //WaterPortability consumerEvent = getFromJson(consumerRecord.value(),consumerRecord.key());
        WaterPortabilityModel consumerModel = consumerRecord.value();
        log.info("Consumer Event : {}",consumerModel);
        int key = consumerRecord.key();
        log.info("Consumer Key : {}",key);
        saveEvent(consumerModel);
    }

    private void saveEvent(WaterPortabilityModel waterPortabilityModel) {
        waterPortabilityRepository.save(waterPortabilityModel);
        log.info("Consumer Event: {} Persisted!",waterPortabilityModel);
    }


}
