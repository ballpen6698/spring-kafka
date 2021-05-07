package com.springboot.config;

import com.springboot.model.Balance;
import com.springboot.model.Customer;
import com.springboot.model.CustomerBalance;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
//    @Value("${kafka.boot.server}")
    private String kafkaServer="localhost:9092";

    @Value("${kafka.consumer.group.customer}")
    private String customerGroupId;

    @Value("${kafka.consumer.group.balance}")
    private String balanceGroupId;

    @Value("${kafka.consumer.group.customerbalance}")
    private String customerBalanceGroupId;

    @Bean
    public ConsumerFactory<String, Customer> customerConsumerConfig() {
        // TODO Auto-generated method stub
        Map<String, Object> config = new HashMap<>();
        // in case rebalance and reconsume
        //config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, customerGroupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config, null, new JsonDeserializer<Customer>(Customer.class));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Customer>> customerKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Customer> listener = new ConcurrentKafkaListenerContainerFactory<>();
        listener.setConsumerFactory(customerConsumerConfig());
        return listener;
    }

    @Bean
    public ConsumerFactory<String, Balance> balanceConsumerConfig() {
        // TODO Auto-generated method stub
        Map<String, Object> config = new HashMap<>();
        // in case rebalance and reconsume
        //config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, balanceGroupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config, null, new JsonDeserializer<Balance>(Balance.class));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Balance>> balanceKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Balance> listener = new ConcurrentKafkaListenerContainerFactory<>();
        listener.setConsumerFactory(balanceConsumerConfig());
        return listener;
    }

    @Bean
    public ConsumerFactory<String, CustomerBalance> customerBalanceConsumerConfig() {
        // TODO Auto-generated method stub
        Map<String, Object> config = new HashMap<>();
        // in case rebalance and reconsume
//        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, customerBalanceGroupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config, null, new JsonDeserializer<CustomerBalance>(CustomerBalance.class));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, CustomerBalance>> customerBalanceKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CustomerBalance> listener = new ConcurrentKafkaListenerContainerFactory<>();
        listener.setConsumerFactory(customerBalanceConsumerConfig());
        return listener;
    }
}
