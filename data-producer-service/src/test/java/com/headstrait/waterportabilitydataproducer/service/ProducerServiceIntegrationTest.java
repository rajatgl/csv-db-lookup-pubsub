package com.headstrait.waterportabilitydataproducer.service;

import com.headstrait.waterportabiliitydataproducer.DataProducerApplication;
import com.headstrait.waterportabiliitydataproducer.model.WaterPortability;
import com.headstrait.waterportabiliitydataproducer.service.FetchDataFromSource;
import com.headstrait.waterportabiliitydataproducer.service.WaterPortabilityEventProducer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.concurrent.ListenableFuture;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DataProducerApplication.class)
@DirtiesContext
@EmbeddedKafka(topics = {"water-portability-events"})
public class ProducerServiceIntegrationTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Mock
    private FetchDataFromSource fetchDataFromSource;


    @InjectMocks
    private WaterPortabilityEventProducer producerService;


    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }



    public WaterPortability mockWaterPotabilityEvent() {
        return new WaterPortability("0.0","0.0","0.0",
                "0.0","0.0","0.0","0.0","0.0","0.0");
    }

    /**
     * Verify the output in the topic with an simulated consumer.
     */
    @Test
    public void itShould_ProduceCorrectExampleDTO_to_TOPIC_EXAMPLE_EXTERNE()
            throws ExecutionException, InterruptedException {
        // GIVEN
        WaterPortability event = mockWaterPotabilityEvent();
        // simulation consumer
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("group_consumer_test",
                "false", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        ConsumerFactory cf = new DefaultKafkaConsumerFactory<String, WaterPortability>(consumerProps, new StringDeserializer(), new JsonDeserializer<>(WaterPortability.class, false));
        Consumer<String, WaterPortability> consumerServiceTest = cf.createConsumer();

        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumerServiceTest, "water-portability-events");
        // WHEN
        Mockito.when(fetchDataFromSource.fetchData())
                .thenReturn(List.of(event));

//        ListenableFuture<SendResult<Integer,WaterPortability>> listenableFuture =
//                producerService.sendJson(event,1);
        producerService.producer();
        // THEN
//        if(listenableFuture.isDone()){
            ConsumerRecord<String, WaterPortability> consumerRecordOfExampleDTO =
                    KafkaTestUtils.getSingleRecord(consumerServiceTest, "water-portability-events");
            WaterPortability valueReceived = consumerRecordOfExampleDTO.value();

            assertEquals(0.0f, valueReceived.getPh());
//        assertEquals("NAME", valueReceived.getName());
            consumerServiceTest.close();
//        }
    }
}
