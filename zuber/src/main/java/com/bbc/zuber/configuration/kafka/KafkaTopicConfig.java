package com.bbc.zuber.configuration.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaTopicConfig {

    @Bean
    public NewTopic driverRegistration() {
        return TopicBuilder.name("driver-registration")
                .build();
    }

    @Bean
    public NewTopic driverDelete() {
        return TopicBuilder.name("driver-deleted")
                .build();
    }

    @Bean
    public NewTopic driverEdit() {
        return TopicBuilder.name("driver-edited")
                .build();
    }

    @Bean
    public NewTopic driverMessage() {
        return TopicBuilder.name("driver-message")
                .build();
    }
}
