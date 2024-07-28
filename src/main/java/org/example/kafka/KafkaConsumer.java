package org.example.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author: zlt
 * @date: 2024/7/5
 * <p>
 * Blog: https://zlt2000.gitee.io
 * Github: https://github.com/zlt2000
 */
@Service
public class KafkaConsumer {
    @KafkaListener(topics = "test", groupId = "test-consumer-group")
    public void consume(String message) {
        System.out.println("Received message: " + message);
    }
}