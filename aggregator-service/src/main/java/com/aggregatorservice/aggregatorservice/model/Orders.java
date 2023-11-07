package com.aggregatorservice.aggregatorservice.model;

import lombok.Data;

@Data
public class Orders {

    private Long id;

    private String item;

    private int quantity;

    private double amount;

    private String status;

}

