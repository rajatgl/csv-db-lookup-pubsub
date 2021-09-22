package com.headstrait.dataconsumer.consumer;

import com.headstrait.dataconsumer.entity.WaterPortabilityModel;
import com.headstrait.dataconsumer.service.WaterPotabilityEventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WaterPortabilityEventConsumer2 {

    @Autowired
    WaterPotabilityEventService waterPotabilityEventService;

    @KafkaListener(topics = {"water-portability-events"})
    public void onMessage1(ConsumerRecord<Integer, WaterPortabilityModel> consumerRecord){

        waterPotabilityEventService.processJsonConsumerEvent(consumerRecord);
        log.info("ConsumerRecord-C2 : {} ", consumerRecord );
    }
}
