package com.headstrait.waterportabiliitydataproducer.producer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import com.headstrait.waterportabiliitydataproducer.model.WaterPortability;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.headstrait.waterportabiliitydataproducer.util.CommonUtil.delay;

@Component
@Slf4j
public class WaterPortabilityEventProducer {

    @Autowired
    KafkaTemplate<Integer, WaterPortability> kafkaTemplate;

    private final String topic = "water-portability-events";

    static ObjectMapper objectMapper = new ObjectMapper();

    @SuppressWarnings("unchecked")
    private static List<WaterPortability> fetchData(){

        List<WaterPortability> listOfEventsToBeSentToTopic = new ArrayList<>();
        try {
            ArrayList<LinkedHashMap<String,String>> hashMaps =  objectMapper
                    .readValue(new URL("http://localhost:9001/get"), ArrayList.class);
            listOfEventsToBeSentToTopic = hashMaps.stream().map(
                    hashMap -> new WaterPortability(hashMap.get("ph"), hashMap.get("Hardness"), hashMap.get("Solids"),
                            hashMap.get("Chloramines"), hashMap.get("Conductivity"), hashMap.get("Organic_carbon"),
                            hashMap.get("Trihalomethanes"), hashMap.get("Turbidity"), hashMap.get("Potability"))
            ).collect(Collectors.toList());
        } catch (JsonProcessingException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (WaterPortability event:
             listOfEventsToBeSentToTopic) {
            System.out.println(event);
        }
        return listOfEventsToBeSentToTopic;
    }


    //ToDo: send event in json format.
    public ListenableFuture<SendResult<Integer,WaterPortability>> sendJson(WaterPortability waterPortability,
                                                             int id) {

        Integer key = id;
        WaterPortability value = waterPortability;

        ProducerRecord<Integer,WaterPortability> producerRecordJson = buildProducerRecordJson(key, value);

        ListenableFuture<SendResult<Integer, WaterPortability>> listenableFutureV2 =
                kafkaTemplate.send(producerRecordJson);

        listenableFutureV2.addCallback(new ListenableFutureCallback<SendResult<Integer, WaterPortability>>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, value, ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, WaterPortability> result) {
                handleSuccess(key, value, result);
            }
        });

        return listenableFutureV2;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void producer() throws JsonProcessingException {
        List<WaterPortability> data = fetchData();
        for(int i=0; i<data.size();i++){
            sendJson(data.get(i),i);
            delay(10000);
        }
    }


    //producer record builder for json send event
    private ProducerRecord<Integer, WaterPortability> buildProducerRecordJson(Integer key, WaterPortability value) {
        return new ProducerRecord<>(topic, key, value);
    }


    //fail handler for json send event
    private void handleFailure(Integer key, WaterPortability value, Throwable ex) {
        log.error("Error Sending the Message and the exception is {}", ex.getMessage());
        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error in OnFailure: {}", throwable.getMessage());
        }
    }

    //success handler for json send event
    private void handleSuccess(Integer key, WaterPortability value, SendResult<Integer, WaterPortability> result) {
        log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}",
                key, value.toString(), result.getRecordMetadata().partition());
    }


}
