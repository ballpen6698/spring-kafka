package com.springboot.service;

import com.springboot.model.Balance;
import com.springboot.model.Customer;
import com.springboot.model.CustomerBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProducerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerService.class);

    @Autowired
    private KafkaTemplate<String, Customer> customerKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, Balance> balanceKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, CustomerBalance> customerBalanceKafkaTemplate;

    @Value("${kafka.topic.customer.name}")
    private String customerTopic;

    @Value("${kafka.topic.customerBalance.name}")
    private String customerBalanceTopic;

    @Value("${kafka.topic.balance.name}")
    private String balanceTopic;

    public void sendCustomerTopic(Customer customer) {
        // TODO Auto-generated method stub
        Map<String, Object> headers = new HashMap<>();
        headers.put(KafkaHeaders.TOPIC, customer);
        customerKafkaTemplate.send(new GenericMessage<Customer>(customer, headers));
        LOGGER.info("Data - " + customer.toString() + " sent to Kafka Topic - " + customerTopic);
    }

    public void sendCustomerBalanceTopic(CustomerBalance customerBalance) {
        // TODO Auto-generated method stub
        Map<String, Object> headers = new HashMap<>();
        headers.put(KafkaHeaders.TOPIC, customerBalanceTopic);
        customerBalanceKafkaTemplate.send(new GenericMessage<CustomerBalance>(customerBalance, headers));
        LOGGER.info("Data - " + customerBalance.toString() + " sent to Kafka Topic - " + customerBalanceTopic);
    }

    public void sendBalanceTopic(Balance balance) {
        // TODO Auto-generated method stub
        Map<String, Object> headers = new HashMap<>();
        headers.put(KafkaHeaders.TOPIC, balanceTopic);
        balanceKafkaTemplate.send(new GenericMessage<Balance>(balance, headers));
        LOGGER.info("Data - " + balance.toString() + " sent to Kafka Topic - " + balanceTopic);
    }
}
