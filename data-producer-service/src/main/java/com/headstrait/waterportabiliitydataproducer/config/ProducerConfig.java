package com.headstrait.waterportabiliitydataproducer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Profile("local")
public class ProducerConfig {

    @Bean
    public NewTopic libraryEvents(){
        return TopicBuilder.name("water-portability-events")
                .partitions(3)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic countEvent(){
        return TopicBuilder.name("count-events")
                .partitions(3)
                .replicas(3)
                .build();
    }

}
