package com.headstrait.waterportabilitydataproducer.intg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.headstrait.waterportabiliitydataproducer.model.WaterPortability;
import com.headstrait.waterportabiliitydataproducer.service.WaterPortabilityEventProducer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProducerTest extends AbstractIntegrationTest{
    private final String topicName = "test";

    @Autowired
    KafkaProperties properties;

    //spy instance retains the features of parent class while letting us stub some features
    @Spy
    ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    WaterPortabilityEventProducer waterPortabilityEventProducer;

    @Test
    void contextLoads() throws IOException {
        WaterPortability waterPortabilityData = new WaterPortability("0.0","0.0","0.0",
                "0.0","0.0","0.0","0.0","0.0","0.0");

        HashMap<String,String> data = new HashMap<>();
        data.put("ph","0.0");
        data.put("hardness","0.0");
        data.put("solids","0.0");
        data.put("chloramines","0.0");
        data.put("conductivity","0.0");
        data.put("organic_carbon","0.0");
        data.put("trihalomethanes","0.0");
        data.put("turbidity","0.0");
        data.put("potability","0.0");

        when(objectMapper.readValue(new URL("http://localhost:9001/get"),
                ArrayList.class)).thenReturn((ArrayList) Collections.singletonList(data));

        final Consumer<Integer,Object> consumer = createConsumer("water-portability-events");

        final List<Object> actualValues = new ArrayList<>();

        while (true)
        {
            ConsumerRecords<Integer, Object> records = KafkaTestUtils.getRecords(consumer,
                    1000);
            if(records.isEmpty())
                break;
            records.forEach(consumerRecord->
                    actualValues.add(consumerRecord.value())
            );
        }
        final List<Object> expectedValues = Collections.singletonList(waterPortabilityData) ;

        assertEquals(expectedValues,actualValues);
    }

    private Consumer<Integer,Object> createConsumer(final String topicName){
        final Consumer<Integer, Object> consumer =
                new DefaultKafkaConsumerFactory<>(
                        properties.buildConsumerProperties(),
                        IntegerDeserializer::new,
                        JsonDeserializer::new).createConsumer();
        consumer.subscribe(Collections.singletonList(topicName));
        return consumer;
    }

}
