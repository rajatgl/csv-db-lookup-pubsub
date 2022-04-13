package com.headstrait.waterportabilitydataproducer.unit;

import com.headstrait.waterportabiliitydataproducer.model.WaterPortability;
import com.headstrait.waterportabiliitydataproducer.service.WaterPortabilityEventProducer;
import com.headstrait.waterportabiliitydataproducer.service.FetchDataFromSource;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DataProducerApplicationUnitTests {

    @Mock
    KafkaTemplate<Integer, WaterPortability> kafkaTemplate;

//    //spy instance retains the features of parent class while letting us stub some features
//    @Spy
//    ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    FetchDataFromSource fetchDataFromSource;

    @InjectMocks
    WaterPortabilityEventProducer producer;

    @Autowired
    KafkaProperties properties;

    @Test
    void sendWaterPotabilityEvent_failure(){
        //given
        WaterPortability waterPortabilityData = new WaterPortability("0.0","0.0","0.0",
                "0.0","0.0","0.0","0.0","0.0","0.0");

                //To be able to set value to future result
        SettableListenableFuture<SendResult<Integer,WaterPortability>> future =
                new SettableListenableFuture<>();

        //expect exception form the future result
        future.setException(new RuntimeException("Exception Calling Kafka"));

        //mock the method that will be invoked and return the expected result.
        when(kafkaTemplate.send(isA(ProducerRecord.class))).thenReturn(future);

        //send method form kafkaTemplate will throw exception when returned, as expected.
        assertThrows(Exception.class, ()->producer.sendJson(waterPortabilityData,14645).get());
    }

    @Test
    void sendWaterPotabilityEvent_success() throws ExecutionException, InterruptedException {
        //given
        WaterPortability waterPortabilityData = new WaterPortability("0.0","0.0","0.0",
                "0.0","0.0","0.0","0.0","0.0","0.0");

        when(fetchDataFromSource.fetchData())
                .thenReturn(List.of(waterPortabilityData));

//        //mock the method that will be invoked and return the expected result.
//        when(kafkaTemplate.send(isA(ProducerRecord.class))).thenReturn(new SettableListenableFuture().set())

//        final Consumer<Integer,Object> consumer = createConsumer("test");

//        final List<Object> actualValues = new ArrayList<>();
//
//        while (true)
//        {
//            ConsumerRecords<Integer, Object> records = KafkaTestUtils.getRecords(consumer,
//                    10000);
//            if(records.isEmpty())
//                break;
//            records.forEach(consumerRecord->
//                    actualValues.add(consumerRecord.value())
//                    );
//        }
//        final List<Object> expectedValues = Collections.singletonList(waterPortabilityData) ;
//
//        assertEquals(expectedValues,actualValues);

        assertEquals(waterPortabilityData,
                producer.sendJson(waterPortabilityData,1).get().getProducerRecord().value());
    }

//    private Consumer<Integer,Object> createConsumer(final String topicName){
//        properties.setBootstrapServers(Collections.singletonList("localhost:9092"));
//        final Consumer<Integer, Object> consumer =
//                new DefaultKafkaConsumerFactory<>(
//                        properties.buildConsumerProperties(),
//                        IntegerDeserializer::new,
//                        JsonDeserializer::new).createConsumer();
//        consumer.subscribe(Collections.singletonList(topicName));
//        return consumer;
//    }


}
