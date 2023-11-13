package com.proxycache.proxy.model;

import lombok.Data;

@Data
public class Orders {

    private Long id;

    private String item;

    private int quantity;

    private double amount;

    private String status;

}