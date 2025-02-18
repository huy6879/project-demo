package com.giahuy.demo.Service;


import com.giahuy.demo.dto.request.OrderCreationRequest;
import com.giahuy.demo.dto.response.OrderResponse;
import com.giahuy.demo.entity.Order;
import com.giahuy.demo.entity.User;
import com.giahuy.demo.exception.AppException;
import com.giahuy.demo.exception.ErrorCode;
import com.giahuy.demo.mapper.OrderMapper;
import com.giahuy.demo.repository.OrderRepository;
import com.giahuy.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {

    OrderRepository orderRepository;
    UserRepository userRepository;
    OrderMapper orderMapper;

//    public OrderResponse createOrder(OrderCreationRequest request) {
//        User user = userRepository.findById(request.getUser_id())
//                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
//
//        Order order = orderMapper.toOrder(request);
//
//        order.setUser_id(user);
//
//        order = orderRepository.save(order);
//
//        return orderMapper.toOrderResponse(order);
//    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderResponse> getOrders() {
        return orderRepository.findAll().stream().map(orderMapper::toOrderResponse).toList();
    }



}
