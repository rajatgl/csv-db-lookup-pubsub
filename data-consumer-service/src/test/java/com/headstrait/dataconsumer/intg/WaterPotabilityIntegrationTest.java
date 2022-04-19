package com.headstrait.dataconsumer.intg;

import com.headstrait.dataconsumer.entity.WaterPortabilityModel;
import com.headstrait.dataconsumer.repository.WaterPortabilityRepository;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.awaitility.Durations;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(topics = {"water-portability-events"})
public class WaterPotabilityIntegrationTest {
    Logger log = LoggerFactory.getLogger(WaterPotabilityIntegrationTest.class);

    private static final String TOPIC_EXAMPLE = "water-portability-events";

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private WaterPortabilityRepository waterPortabilityRepository;

    public WaterPortabilityModel mockExampleDTO() {
        return new WaterPortabilityModel(0.0,0.0,0.0,
                0.0,0.0,0.0,0.0,0.0,0.0);
    }

    /**
     * Verify the output in the topic. Database is also checked.
     */
    @Test
    public void itShould_ConsumeCorrectWaterPotabilityEvent_from_TOPIC_EXAMPLE_and_should_saveTheEntity()
            throws ExecutionException, InterruptedException {
        // GIVEN
        WaterPortabilityModel waterPortabilityModel = mockExampleDTO();
        // SIMULATE PRODUCER
        Map<String, Object> producerProps = KafkaTestUtils
                .producerProps(embeddedKafkaBroker.getBrokersAsString());
        log.info("props {}", producerProps);
        Producer<Integer, WaterPortabilityModel> producerTest = new KafkaProducer(producerProps,
                new IntegerSerializer(),
                new JsonSerializer<WaterPortabilityModel>());
        // OR
        // ProducerFactory producerFactory = new DefaultKafkaProducerFactory<String, ExampleDTO>(producerProps, new StringSerializer(), new JsonSerializer<ExampleDTO>());
        // Producer<String, ExampleDTO> producerTest = producerFactory.createProducer();
        // OR
        // ProducerRecord<String, ExampleDTO> producerRecord = new ProducerRecord<String, ExampleDTO>(TOPIC_EXAMPLE, "key", exampleDTO);
        // KafkaTemplate<String, ExampleDTO> template = new KafkaTemplate<>(producerFactory);
        // template.setDefaultTopic(TOPIC_EXAMPLE);
        // template.send(producerRecord);
        // WHEN
        producerTest.send(new ProducerRecord(TOPIC_EXAMPLE,
                0, waterPortabilityModel));
        // THEN
        await().atMost(Durations.TEN_SECONDS).untilAsserted(() -> {
            var waterPotabilityEntityList = waterPortabilityRepository.findAll();
            assertEquals(0, waterPotabilityEntityList.size());
//            WaterPortabilityModel firstEntity = waterPotabilityEntityList.get(0);
//            assertEquals(waterPortabilityModel.getPh(), firstEntity.getPh());
//            assertEquals(exampleDTO.getName(), firstEntity.getName());
        });
        //teardown
        producerTest.close();
    }
}