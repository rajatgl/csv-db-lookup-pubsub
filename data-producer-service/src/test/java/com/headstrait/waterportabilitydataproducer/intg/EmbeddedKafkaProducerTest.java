package com.headstrait.waterportabilitydataproducer.intg;

import com.headstrait.waterportabiliitydataproducer.DataProducerApplication;
import com.headstrait.waterportabiliitydataproducer.model.WaterPortability;
import com.headstrait.waterportabiliitydataproducer.service.WaterPortabilityEventProducer;
import com.headstrait.waterportabiliitydataproducer.service.FetchDataFromSource;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DataProducerApplication.class)
@DirtiesContext
@EmbeddedKafka(topics = {"water-portability-events", "test-topic"})
public class EmbeddedKafkaProducerTest {
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private WaterPortabilityEventProducer producer;

    @Mock
    FetchDataFromSource fetchDataFromSource;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    public WaterPortability mockWaterPotabilityEvent() {
        return new WaterPortability("0.0","0.0","0.0",
                "0.0","0.0","0.0","0.0","0.0","0.0");
    }

    /**
     * We verify the output in the topic. With an simulated consumer.
     */
    @Test
    @Timeout(10)
    public void itShould_ProduceCorrectExampleDTO_to_TOPIC_EXAMPLE_EXTERNE() {
        // GIVEN
        WaterPortability mockEvent = mockWaterPotabilityEvent();
        // simulation consumer
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("test_consumer",
                "false",
                embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        ConsumerFactory consumerFactory = new DefaultKafkaConsumerFactory<String, WaterPortability>(consumerProps,
                new StringDeserializer(),
                new JsonDeserializer<>(WaterPortability.class,
                        false));
        Consumer<String, WaterPortability> testConsumer = consumerFactory.createConsumer();

        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(testConsumer, "water-portability-events");

//        when(fetchDataFromSource.fetchData())
//                .thenReturn(List.of(mockEvent));
//        // WHEN
//        producer.sendJson(mockEvent,1);
        // THEN
        ConsumerRecord<String, WaterPortability> consumerRecordOfWaterPotability = KafkaTestUtils
                .getSingleRecord(testConsumer, "water-portability-events");
        WaterPortability valueReceived = consumerRecordOfWaterPotability.value();

        assertEquals(null, valueReceived.getPh());
//        assertEquals(0.0f, valueReceived.getHardness());

        testConsumer.close();
    }
}
