package com.bbc.zuber.configuration.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaTopicConfig {

    @Bean
    public NewTopic userRegistration() {
        return TopicBuilder.name("driver-registration")
                .build();
    }

    @Bean
    public NewTopic userDelete() {
        return TopicBuilder.name("driver-deleted")
                .build();
    }

    @Bean
    public NewTopic userEdit() {
        return TopicBuilder.name("driver-edited")
                .build();
    }
}
