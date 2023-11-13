package com.proxycache.proxy.service;

import com.proxycache.proxy.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class ProxyServiceCaching {
    @Autowired
    RestTemplate restTemplate;
//    @Cacheable(value = "orderCache", key = "#orderId")
//    @Async
//    @Scheduled(fixedRate = 60000) // Refresh every 60 seconds
//    @CacheEvict(value = "orderCache", allEntries = true)
//    public CompletableFuture<Void> refreshCache() {
//        // Fetch updated data from the Order service and update the cache
//        // ...
//        return CompletableFuture.completedFuture(null);
//    }
    @Cacheable(value = "orderCache", key = "#orderId")
    public ResponseEntity<Orders> getOrder(String orderId) {
        String orderApiUrl="http://localhost:8080/api/orders/"+orderId;
        Orders orderResponse = restTemplate.getForObject(orderApiUrl, Orders.class, orderId);
        System.out.println("successful printed>>>>>>>>>>>"+orderResponse);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @CacheEvict(value = "orderCache", allEntries = true)
    @KafkaListener(topics = "update-order", groupId = "orders-group")
    public void handleOrderUpdate(Long orderId) {
        System.out.println("in kafka listener>>>>>>>>>."+orderId);
    }

}
