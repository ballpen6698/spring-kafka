package com.springboot.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class Balance {
    String balanceId;
    String accountId;
    float balance;
}
