package com.order.ms.controller;

import com.order.ms.entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import com.order.ms.dto.CustomerOrder;
import com.order.ms.dto.OrderEvent;
import com.order.ms.entity.OrderRepository;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private OrderRepository repository;

	@Autowired
	private KafkaTemplate<String, OrderEvent> kafkaTemplate;
	@Autowired
	private KafkaTemplate<String, Long> updateKafkaTemplate;

	@PostMapping("/orders")
	public void createOrder(@RequestBody CustomerOrder customerOrder) {
		Orders order = new Orders();

		try {
			order.setAmount(customerOrder.getAmount());
			order.setItem(customerOrder.getItem());
			order.setQuantity(customerOrder.getQuantity());
			order.setStatus("CREATED");

			order = repository.save(order);

			customerOrder.setOrderId(order.getId());

			OrderEvent event = new OrderEvent();
			event.setOrder(customerOrder);
			event.setType("ORDER_CREATED");
			kafkaTemplate.send("new-orders", event);
		} catch (Exception e) {
			order.setStatus("FAILED");
			repository.save(order);
		}
	}

	@PutMapping("/orders/{orderId}")
	@CacheEvict(value = "orderCache", key = "#orderId")
	public void updateOrder(@PathVariable long orderId,@RequestBody CustomerOrder customerOrder) {
		Optional<Orders> order = repository.findById(orderId);
		if(order.isPresent()) {
			try {
				order.get().setAmount(customerOrder.getAmount());
				order.get().setItem(customerOrder.getItem());
				order.get().setQuantity(customerOrder.getQuantity());
				order.get().setStatus("UPDATED");
				repository.save(order.get());
				OrderEvent event = new OrderEvent();
				event.setOrder(customerOrder);
				event.setType("ORDER-UPDATED");
				updateKafkaTemplate.send("update-order", orderId);
			} catch (Exception e) {
				order.get().setStatus("FAILED");
				repository.save(order.get());
			}
		}else{
			System.out.println("No ");
		}
	}
	@GetMapping("/orders/{orderId}")
	public ResponseEntity getOrder(@PathVariable long orderId) {
		try {
			Optional<Orders> orders=repository.findById(orderId);
			System.out.println("Order service has been called*********************"+orders);
			if(orders.isPresent()){
				return new ResponseEntity(orders, HttpStatus.OK);
			}
			return new ResponseEntity(orders, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			System.out.println("Exception in order>>>"+e.getMessage());
			return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
