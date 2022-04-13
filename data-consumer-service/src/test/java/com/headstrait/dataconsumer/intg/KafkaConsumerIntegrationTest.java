package com.headstrait.dataconsumer.intg;

import com.headstrait.dataconsumer.entity.WaterPortabilityModel;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class KafkaConsumerIntegrationTest {
    WaterPortabilityModel event = new WaterPortabilityModel(0.0,0.0,0.0,
            0.0,0.0,0.0,0.0,0.0,0.0);
}
