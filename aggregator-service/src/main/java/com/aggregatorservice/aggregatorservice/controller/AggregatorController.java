package com.aggregatorservice.aggregatorservice.controller;

//import com.aggregatorservice.aggregatorservice.config.WebConfig;
import com.aggregatorservice.aggregatorservice.model.AggregateResponse;
import com.aggregatorservice.aggregatorservice.model.Delivery;
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



    @GetMapping("/get-order-details/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable long orderId){
        AggregateResponse aggregateResponse = new AggregateResponse();
        RestTemplate restTemplate =new RestTemplate();
        //call order service
        String orderApiUrl="http://localhost:8080/api/orders/"+orderId;
        Orders orderResponse = restTemplate.getForObject(orderApiUrl, Orders.class, orderId);
        //call payment service
        String paymentApiUrl="http://localhost:8081/get-payment-info/"+orderId;
        Payment paymentResponse = restTemplate.getForObject(paymentApiUrl, Payment.class, orderId);
        //call delivery service
        String deliveryApiUrl="http://localhost:8083/get-delivery-info/"+orderId;
        Delivery deliveryResponse = restTemplate.getForObject(deliveryApiUrl, Delivery.class, orderId);

        aggregateResponse.setOrder(orderResponse);
        aggregateResponse.setPayment(paymentResponse);
        if(deliveryResponse==null){
            Delivery d=new Delivery();
            d.setStatus("Delivery not completed since payment failed");
            aggregateResponse.setDelivery(d);
        }else{
            aggregateResponse.setDelivery(deliveryResponse);
        }
        return new ResponseEntity<>(aggregateResponse,HttpStatus.OK);
    }
}
