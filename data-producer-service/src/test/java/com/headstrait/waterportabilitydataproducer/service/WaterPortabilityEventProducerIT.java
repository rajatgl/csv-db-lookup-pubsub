package com.headstrait.waterportabilitydataproducer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.headstrait.waterportabiliitydataproducer.DataProducerApplication;
import com.headstrait.waterportabiliitydataproducer.model.WaterPortability;
import com.headstrait.waterportabiliitydataproducer.service.DataFetcher;
import com.headstrait.waterportabiliitydataproducer.service.WaterPortabilityEventProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.headstrait.waterportabilitydataproducer.constants.TestObjects.WATER_PORTABILITY_EVENT;
import static com.headstrait.waterportabilitydataproducer.constants.TestObjects.WATER_PORTABILITY_EVENTS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.kafka.test.assertj.KafkaConditions.key;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasValue;


@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest(classes = DataProducerApplication.class)
public class WaterPortabilityEventProducerIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(WaterPortabilityEventProducerIT.class);

    private static String TOPIC_NAME = "water-portability-events";

    @MockBean
    DataFetcher dataFetcher;

    @Autowired
    private WaterPortabilityEventProducer waterPortabilityEventProducer;

    private KafkaMessageListenerContainer<Integer, WaterPortability> kafkaMessageListenerContainer;

    private BlockingQueue<ConsumerRecord<Integer, String>> consumerRecords;


    @ClassRule
    public static EmbeddedKafkaRule embeddedKafkaRule = new EmbeddedKafkaRule(1,
            true,
            TOPIC_NAME);

    @Before
    public void setUp() {
        consumerRecords = new LinkedBlockingQueue<>();

        ContainerProperties containerProperties = new ContainerProperties(TOPIC_NAME);

        Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps(
                "sender", "false", embeddedKafkaRule.getEmbeddedKafka());

        DefaultKafkaConsumerFactory<Integer, WaterPortability> consumer =
                new DefaultKafkaConsumerFactory<>(consumerProperties);

        kafkaMessageListenerContainer = new KafkaMessageListenerContainer<>(consumer,
                containerProperties);
        kafkaMessageListenerContainer.setupMessageListener((MessageListener<Integer, String>) record -> {
            LOGGER.debug("Listened message='{}'", record.toString());
            consumerRecords.add(record);
        });
        kafkaMessageListenerContainer.start();

        ContainerTestUtils.waitForAssignment(kafkaMessageListenerContainer,
                embeddedKafkaRule.getEmbeddedKafka().getPartitionsPerTopic());
    }

    @After
    public void tearDown() {
        kafkaMessageListenerContainer.stop();
    }

    @Test
    public void it_should_send_water_potability_event()
            throws InterruptedException, IOException {

        waterPortabilityEventProducer.sendJson(WATER_PORTABILITY_EVENT,1);

        ConsumerRecord<Integer, String> received =
                consumerRecords.poll(10, TimeUnit.SECONDS);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString( WATER_PORTABILITY_EVENT );

        assertThat(received, hasValue(json));
        assertThat(received).has(key(1));
    }

    @Test
    public void it_should_send_water_potability_events()
            throws InterruptedException, IOException {

        Mockito.when(dataFetcher.fetchData())
                .thenReturn(WATER_PORTABILITY_EVENTS);


        waterPortabilityEventProducer.produce();

        ConsumerRecord<Integer, String> received =
                consumerRecords.poll(10, TimeUnit.SECONDS);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(WATER_PORTABILITY_EVENTS.get(0));

        //assert for first event
        assertThat(received, hasValue(json));
        assertThat(received).has(key(0));

        ConsumerRecord<Integer, String> receivedSecondRecord =
                consumerRecords.poll(10, TimeUnit.SECONDS);

        String json2 = mapper.writeValueAsString(WATER_PORTABILITY_EVENTS.get(1));

        //assert for second event
        assertThat(receivedSecondRecord, hasValue(json2));
        assertThat(receivedSecondRecord).has(key(1));


    }
}