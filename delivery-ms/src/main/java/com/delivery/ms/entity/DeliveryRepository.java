package com.delivery.ms.entity;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DeliveryRepository extends CrudRepository<Delivery, Long> {

    Optional<Delivery> findByOrderId(Long orderId);
}
