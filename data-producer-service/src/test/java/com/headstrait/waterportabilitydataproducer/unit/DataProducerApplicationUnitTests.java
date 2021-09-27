package com.headstrait.waterportabilitydataproducer.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.headstrait.waterportabiliitydataproducer.model.WaterPortability;
import com.headstrait.waterportabiliitydataproducer.producer.WaterPortabilityEventProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.SettableListenableFuture;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DataProducerApplicationUnitTests {

    @Mock
    KafkaTemplate<Integer, WaterPortability> kafkaTemplate;

    //spy instance retains the features of parent class while letting us stub some features
    @Spy
    ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    WaterPortabilityEventProducer producer;


    @Test
    void sendWaterPotabilityEvent_failure(){
        //given
        WaterPortability waterPortability = WaterPortability.builder()
                .ph(0.0f)
                .hardness(0.0f)
                .solids(0.0f)
                .chloramines(0.0f)
                .conductivity(0.0f)
                .organic_carbon(0.0f)
                .trihalomethanes(0.0f)
                .turbidity(0.0f)
                .potability(0.0f)
                .build();

        WaterPortability waterPortabilityData = new WaterPortability("0.0","0.0","0.0",
                "0.0","0.0","0.0","0.0","0.0","0.0");

                //To be able to set value to future result
        SettableListenableFuture future = new SettableListenableFuture();

        //expect exception form the future result
        future.setException(new RuntimeException("Exception Calling Kafka"));

        //mock the method that will be invoked and return the expected result.
        when(kafkaTemplate.send(isA(ProducerRecord.class))).thenReturn(future);

        //send method form kafkaTemplate will throw exception when returned, as expected.
        assertThrows(Exception.class, ()->producer.sendJson(waterPortabilityData,14645).get());
    }





}
