package com.aggregatorservice.aggregatorservice.model;

import lombok.Data;

@Data
public class Payment {

    private Long id;

    private String mode;

    private Long orderId;

    private double amount;

    private String status;
}
