package org.example.kafka.service;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author: zlt
 * @date: 2024/7/5
 * <p>
 * Blog: https://zlt2000.gitee.io
 * Github: https://github.com/zlt2000
 */
@Service
@AllArgsConstructor
public class KafkaTopicService {
    private final KafkaAdminClient kafkaAdminClient;

    public void createTopic(String topicName, int partitions, short replicationFactor) throws ExecutionException, InterruptedException {
        NewTopic newTopic = new NewTopic(topicName, partitions, replicationFactor);
        CreateTopicsResult result = kafkaAdminClient.createTopics(List.of(newTopic));
        result.all().get();
    }

    public void delTopic(String topicName) throws ExecutionException, InterruptedException {
        DeleteTopicsResult result = kafkaAdminClient.deleteTopics(List.of(topicName));
        result.all().get();
    }

    public Collection<String> listTopic() throws ExecutionException, InterruptedException {
        ListTopicsResult result = kafkaAdminClient.listTopics();
        Collection<TopicListing> topics = result.listings().get();
        return topics.stream().map(TopicListing::name).toList();
    }
}
