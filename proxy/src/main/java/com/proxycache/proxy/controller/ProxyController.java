package com.proxycache.proxy.controller;

import com.proxycache.proxy.model.Orders;
import com.proxycache.proxy.service.ProxyServiceCaching;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxy")
public class ProxyController {
    @Autowired
    private ProxyServiceCaching proxyService;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Orders> getOrder(@PathVariable String orderId) {
        // Proxy service handles the request

        return proxyService.getOrder(orderId);
    }
}
