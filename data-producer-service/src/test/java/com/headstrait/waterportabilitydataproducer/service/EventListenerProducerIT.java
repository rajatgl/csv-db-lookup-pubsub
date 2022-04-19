package com.headstrait.waterportabilitydataproducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.headstrait.waterportabiliitydataproducer.DataProducerApplication;
import com.headstrait.waterportabiliitydataproducer.model.WaterPortability;
import com.headstrait.waterportabiliitydataproducer.service.DataFetcher;
import com.headstrait.waterportabiliitydataproducer.service.WaterPortabilityEventProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

//import static org.junit.Assert.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.kafka.test.assertj.KafkaConditions.key;

@RunWith(SpringRunner.class)
//@ExtendWith(MockitoExtension.class)
@DirtiesContext
@SpringBootTest(classes = DataProducerApplication.class)
public class EventListenerProducerIT {

    @MockBean
    ObjectMapper objectMapper;

//    @InjectMocks
//    DataFetcher dataFetcher;

//    @TestConfiguration
//    public static class TestConfig {
//
//        @Bean
//        @Primary
//        public DataFetcher dataFetcher() {
//            List<WaterPortability> waterPortabilityEvents=
//                    List.of(new WaterPortability("0.0","0.0","0.0",
//                            "0.0","0.0","0.0",
//                            "0.0","0.0","0.0"));
//            DataFetcher fetcher = mock(DataFetcher.class);
//            when(fetcher.fetchData())
//                    .thenReturn(waterPortabilityEvents);
//            return fetcher;
//        }
//
//    }

//    @MockBean
//    WaterPortabilityEventProducer producer;

    private KafkaMessageListenerContainer<Integer, WaterPortability> kafkaMessageListenerContainer;

    private BlockingQueue<ConsumerRecord<Integer, String>> consumerRecords;

    private static String TOPIC_NAME = "water-portability-events";

    private static final Logger LOGGER = LoggerFactory.getLogger(EventListenerProducerIT.class);


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
    public void test()
            throws InterruptedException, IOException {
        List<WaterPortability> waterPortabilityEvents=
                List.of(new WaterPortability("0.0","0.0","0.0",
                        "0.0","0.0","0.0",
                        "0.0","0.0","0.0"));

        HashMap<String,String> data = new HashMap<>();
        data.put("ph","0.0");
        data.put("Hardness","0.0");
        data.put("Solids","0.0");
        data.put("Chloramines","0.0");
        data.put("Conductivity","0.0");
        data.put("Organic_carbon","0.0");
        data.put("Trihalomethanes","0.0");
        data.put("Turbidity","0.0");
        data.put("Potability","0.0");

//        when(objectMapper.readValue(any(URL.class),
//                List.class)).thenReturn(Collections.singletonList(data));

//        when(fetcher.fetchData())
//                .thenReturn(waterPortabilityEvents);

//        AtomicBoolean isTested = new AtomicBoolean(false);
//
//        doAnswer(answer->{
//            isTested.set(true);
//            System.out.println("TEST");
//            return null;
//        }).when(producer).produce();

        ConsumerRecord<Integer, String> received =
                consumerRecords.poll(10, TimeUnit.SECONDS);

        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(waterPortabilityEvents.get(0));

//        assertThat(received, hasValue(json));
        Assertions.assertThat(received).has(key(0));

//        TimeUnit.SECONDS.sleep(10);
//        assertTrue(isTested.get());
    }
}
