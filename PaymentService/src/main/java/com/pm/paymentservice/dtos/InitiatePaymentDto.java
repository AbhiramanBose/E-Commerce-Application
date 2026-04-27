package com.pm.paymentservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitiatePaymentDto {
    Long amount;
    String orderId;
    String phoneNumber;
    String name;
    String email;
}
