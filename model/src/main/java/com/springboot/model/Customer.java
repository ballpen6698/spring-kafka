package com.springboot.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class Customer {
     private String customerId;
     private String name;
     private String phoneNumber;
     private String accountId;
}
