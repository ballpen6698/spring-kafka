package com.springboot.service;

import com.springboot.model.Balance;
import com.springboot.model.Customer;
import com.springboot.model.CustomerBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsumerService {

    @Autowired
    ProducerService producerService;
    // list is used as fake database to store incoming messages
    // so that when there is matched accountId in customer and balance
    // customerbalance can be produced
    private static List<Customer> customers = new ArrayList<>();
    private static List<Balance> balances = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerService.class);

    @KafkaListener(topics = "${kafka.topic.customer.name}", groupId = "${kafka.consumer.group.customer}"
    , containerFactory = "customerKafkaListenerContainerFactory")
    public void recieveCustomer(Customer customer) {
        if(processCustomer(customer)) {
            customers.add(customer);
        }
        LOGGER.info("Data - customer " + customer.toString() + " recieved");
    }

    @KafkaListener(topics = "${kafka.topic.balance.name}", groupId = "${kafka.consumer.group.balance}"
            , containerFactory = "balanceKafkaListenerContainerFactory")
    public void recieveBalance(Balance balance) {
        if(processBalance(balance)) {
            balances.add(balance);
        }
        LOGGER.info("Data - balance " + balance.toString() + " recieved");
    }

    @KafkaListener(topics = "${kafka.topic.customerBalance.name}", groupId = "${kafka.consumer.group.customerbalance}"
            , containerFactory = "customerBalanceKafkaListenerContainerFactory")
    public void recieveCustomerBalance(CustomerBalance customerBalance) {
        LOGGER.info("Data - customerBalance" + customerBalance.toString() + " recieved");
    }

    private boolean processCustomer(Customer customer){
        List<Balance> operatedList = new ArrayList<>();
        balances.stream().forEach(balance -> {
            if(balance.getAccountId().equals(customer.getAccountId())){
                CustomerBalance customerBalance = CustomerBalance.builder().balance(balance.getBalance())
                        .customerId(customer.getCustomerId()).accountId(customer.getAccountId()).phoneNumber(customer.getPhoneNumber()).build();
                producerService.sendCustomerBalanceTopic(customerBalance);
                operatedList.add(balance);
                return;
            }
        });
        balances.removeAll(operatedList);
        return operatedList.isEmpty();
    }

    private boolean processBalance(Balance balance){
        List<Customer> operatedList = new ArrayList<>();
        customers.stream().forEach(customer -> {
            if(balance.getAccountId().equals(customer.getAccountId())){
                CustomerBalance customerBalance = CustomerBalance.builder().balance(balance.getBalance())
                        .customerId(customer.getCustomerId()).accountId(customer.getAccountId()).phoneNumber(customer.getPhoneNumber()).build();
                producerService.sendCustomerBalanceTopic(customerBalance);
                operatedList.add(customer);
                return;
            }
        });
        customers.removeAll(operatedList);
        return operatedList.isEmpty();
    }
}
