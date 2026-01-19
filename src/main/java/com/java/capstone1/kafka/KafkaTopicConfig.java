package com.java.capstone1.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic dealCreatedTopic() {
        return TopicBuilder.name(KafkaTopics.DEAL_CREATED)
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    NewTopic dealStageUpdated() {
        return TopicBuilder.name(KafkaTopics.DEAL_STAGE_UPDATED).build();
    }

    @Bean
    NewTopic dealValueUpdated() {
        return TopicBuilder.name(KafkaTopics.DEAL_VALUE_UPDATED).build();
    }

    @Bean
    NewTopic dealTypeUpdated() {
        return TopicBuilder.name(KafkaTopics.DEAL_TYPE_UPDATED).build();
    }

    @Bean
    NewTopic dealSectorUpdated() {
        return TopicBuilder.name(KafkaTopics.DEAL_SECTOR_UPDATED).build();
    }

    @Bean
    NewTopic dealSummaryUpdated() {
        return TopicBuilder.name(KafkaTopics.DEAL_SUMMARY_UPDATED).build();
    }

    @Bean
    NewTopic dealNoteAdded() {
        return TopicBuilder.name(KafkaTopics.DEAL_NOTE_ADDED).build();
    }

    @Bean
    NewTopic dealDeleted() {
        return TopicBuilder.name(KafkaTopics.DEAL_DELETED).build();
    }
}
