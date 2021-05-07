package com.springboot.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBalance {
    String accountId;
    String customerId;
    String phoneNumber;
    float balance;
}
