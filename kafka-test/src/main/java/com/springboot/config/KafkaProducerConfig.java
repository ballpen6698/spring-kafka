package com.springboot.config;
import java.util.HashMap;
import java.util.Map;

import com.springboot.model.Balance;
import com.springboot.model.Customer;
import com.springboot.model.CustomerBalance;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaProducerConfig {
    @Value("${kafka.boot.server}")
    private String kafkaServer;

    @Bean
    public ProducerFactory<String, Customer> customerProducerConfig() {
        Map<String, Object> config = new HashMap<>();
        // Confirm receive but not written confirmation
        config.put(ProducerConfig.ACKS_CONFIG, 1);
        // increase linger time to increase throughput
        config.put(ProducerConfig.LINGER_MS_CONFIG, 1000);
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Customer> userKafkaTemplate() {
        return new KafkaTemplate<>(customerProducerConfig());
    }

    @Bean
    public ProducerFactory<String, Balance> balanceProducerConfig() {
        Map<String, Object> config = new HashMap<>();
        // Confirm receive but not written confirmation
        config.put(ProducerConfig.ACKS_CONFIG, 1);
        // increase linger time to increase throughput
        config.put(ProducerConfig.LINGER_MS_CONFIG, 1000);
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Balance> balanceKafkaTemplate() {
        return new KafkaTemplate<>(balanceProducerConfig());
    }

    @Bean
    public ProducerFactory<String, CustomerBalance> customerBalanceProducerConfig() {
        Map<String, Object> config = new HashMap<>();
        // Confirm receive but not written confirmation
        config.put(ProducerConfig.ACKS_CONFIG, "1");
        // increase linger time to increase throughput
        config.put(ProducerConfig.LINGER_MS_CONFIG, 1000);
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, CustomerBalance> customerBalanceKafkaTemplate() {
        return new KafkaTemplate<>(customerBalanceProducerConfig());
    }
}
