package com.springboot.controller;

import com.springboot.model.Balance;
import com.springboot.model.Customer;
import com.springboot.model.CustomerBalance;
import com.springboot.service.ProducerService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/kafkaProducer")
public class ProducerController {
    @Autowired
    private ProducerService sender;

    // fake database, pretend list is database to store customer information
    private static List<Customer> customers = new ArrayList();

    // fake database, pretend list is database to store balances information
    private static List<Balance> balances = new ArrayList();

    @PostMapping("/customer")
    public ResponseEntity<String> sendData(@RequestBody Customer customer){
        sender.sendCustomerTopic(customer);
        customers.add(customer);
        return new ResponseEntity<>("customer sent to Kafka", HttpStatus.OK);
    }

    @PostMapping("/balance")
    public ResponseEntity<String> sendData(@RequestBody Balance balance){
        sender.sendBalanceTopic(balance);
        balances.add(balance);
        return new ResponseEntity<>("balance sent to Kafka", HttpStatus.OK);
    }
}
