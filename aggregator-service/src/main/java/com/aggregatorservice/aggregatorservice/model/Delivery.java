package com.aggregatorservice.aggregatorservice.model;


import lombok.Data;




@Data
public class Delivery {

    private Long id;
    private String address;
    private String status;
    private long orderId;

}
