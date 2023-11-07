package com.aggregatorservice.aggregatorservice.model;

import lombok.Data;

@Data
public class AggregateResponse {
    Orders order;
    Payment payment;
}
