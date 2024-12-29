package com.giahuy.demo.repository;

import com.giahuy.demo.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetails, Integer> {
}
