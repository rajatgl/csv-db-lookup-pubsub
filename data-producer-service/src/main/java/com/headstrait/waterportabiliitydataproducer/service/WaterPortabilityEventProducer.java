package com.headstrait.waterportabiliitydataproducer.service;


import com.headstrait.waterportabiliitydataproducer.model.WaterPortability;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;

import static com.headstrait.waterportabiliitydataproducer.util.CommonUtil.delay;

@Service
@Slf4j
public class WaterPortabilityEventProducer {

    @Autowired
    KafkaTemplate<Integer, WaterPortability> kafkaTemplate;

    @Autowired
    FetchDataFromSource fetchDataFromSource;

    private final String topic = "water-portability-events";

    //ToDo: send event in json format.
    public ListenableFuture<SendResult<Integer, WaterPortability>>
    sendJson(WaterPortability waterPortability,
             int id) {

        Integer key = id;
        WaterPortability value = waterPortability;

        ProducerRecord<Integer,WaterPortability> producerRecordJson = buildProducerRecordJson(key, value);

        ListenableFuture<SendResult<Integer, WaterPortability>> listenableFuture =
                kafkaTemplate.send(producerRecordJson);
        return listenableFuture;
    }

//    @EventListener(ApplicationReadyEvent.class)
    public void producer() {
        List<WaterPortability> data = fetchDataFromSource.fetchData();
        for(int i=0; i<data.size();i++){
            int key = i;
            WaterPortability value = data.get(i);
            sendJson(value,key)
            .addCallback(new ListenableFutureCallback<>() {
                @Override
                public void onFailure(Throwable ex) {
                    handleFailure(key, value, ex);
                }

                @Override
                public void onSuccess(SendResult<Integer, WaterPortability> result) {
                    handleSuccess(key, value, result);
                }
            });
            delay(10000);
        }
    }


    //producer record builder for json send event
    private ProducerRecord<Integer, WaterPortability>
    buildProducerRecordJson(Integer key, WaterPortability value) {
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
