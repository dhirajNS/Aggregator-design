package com.aggregatorservice.aggregatorservice.controller;

import com.aggregatorservice.aggregatorservice.model.AggregateResponse;
import com.aggregatorservice.aggregatorservice.model.Orders;
import com.aggregatorservice.aggregatorservice.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/agg")
public class AggregatorController {
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/get-order-details/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable long orderId){
        AggregateResponse aggregateResponse = new AggregateResponse();
        //call order service
        String orderApiUrl="http://localhost:8080/api/orders";
        Orders orderResponse = restTemplate.getForObject(orderApiUrl, Orders.class, orderId);
        //call payment service
        String paymentApiUrl="http://localhost:8081/get-payment-info";
        Payment paymentResponse = restTemplate.getForObject(paymentApiUrl, Payment.class, orderId);
        aggregateResponse.setOrder(orderResponse);
        aggregateResponse.setPayment(paymentResponse);
        return new ResponseEntity<>(aggregateResponse,HttpStatus.OK);
    }
}
