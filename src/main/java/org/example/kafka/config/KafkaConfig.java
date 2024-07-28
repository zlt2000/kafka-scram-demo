package org.example.kafka.config;

import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

/**
 * @author: zlt
 * @date: 2024/7/5
 * <p>
 * Blog: https://zlt2000.gitee.io
 * Github: https://github.com/zlt2000
 */
@Configuration
public class KafkaConfig {
    @Bean
    public KafkaAdminClient kafkaAdminClient(KafkaAdmin kafkaAdmin) {
        return (KafkaAdminClient) KafkaAdminClient.create(kafkaAdmin.getConfigurationProperties());
    }
}
