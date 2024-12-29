package com.giahuy.demo.mapper;

import com.giahuy.demo.dto.request.OrderCreationRequest;
import com.giahuy.demo.dto.response.OrderResponse;
import com.giahuy.demo.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "user_id", ignore = true)
    Order toOrder(OrderCreationRequest request);

    OrderResponse toOrderResponse(Order order);

}
