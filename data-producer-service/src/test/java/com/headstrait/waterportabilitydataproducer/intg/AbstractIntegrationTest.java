package com.headstrait.waterportabilitydataproducer.intg;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testcontainers.containers.KafkaContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {
    static KafkaContainer kafkaContainer = new KafkaContainer();

    static void kafkaProperties(DynamicPropertyRegistry registry){
        kafkaContainer.start();
        registry.add("spring.kafka.properties.bootstrap.servers",
                kafkaContainer::getBootstrapServers);
        registry.add("spring.kafka.consumer.properties.auto.offset.reset",
                ()->"earliest");
    }
}
