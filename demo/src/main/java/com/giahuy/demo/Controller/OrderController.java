package com.giahuy.demo.Controller;


import com.giahuy.demo.Service.OrderService;
import com.giahuy.demo.dto.response.ApiResponse;
import com.giahuy.demo.dto.response.OrderResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;

//    @PostMapping
//    ApiResponse<OrderResponse> createOrder(@RequestBody OrderCreationRequest request){
//        return ApiResponse.<OrderResponse>builder()
//                .result(orderService.createOrder(request))
//                .build();
//    }

    @GetMapping
    ApiResponse <List<OrderResponse>> getAllOrders(){
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getOrders())
                .build();
    }


}
