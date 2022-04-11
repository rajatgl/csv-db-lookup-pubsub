package com.headstrait.waterportabilitydataproducer.intg;

import com.headstrait.waterportabiliitydataproducer.model.WaterPortability;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProducerTest extends AbstractIntegrationTest{
    private final String topicName = "test";

    @Autowired
    KafkaProperties properties;

    @Test
    void contextLoads(){
        WaterPortability waterPortabilityData = new WaterPortability("0.0","0.0","0.0",
                "0.0","0.0","0.0","0.0","0.0","0.0");

        final Consumer<Integer,Object> consumer = createConsumer("test");

        final List<Object> actualValues = new ArrayList<>();

        while (true)
        {
            ConsumerRecords<Integer, Object> records = KafkaTestUtils.getRecords(consumer,
                    10000);
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
