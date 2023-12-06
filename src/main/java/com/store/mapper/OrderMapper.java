package com.store.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.DTO.ClientDTO;
import com.store.DTO.OrderDTO;
import com.store.entities.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    ObjectMapper mapper;
    OrderProductMapper orderProductMapper;

    public OrderMapper(ObjectMapper mapper, OrderProductMapper orderProductMapper) {
        this.mapper = mapper;
        this.orderProductMapper = orderProductMapper;
    }

    public OrderDTO maptoDTO(Order order){
        return new OrderDTO(mapper.convertValue(order.getClient(), ClientDTO.class),
                order.getCreatedDate(),
                order.getOrderProducts().stream().map(orderProductMapper::mapToDTO).toList(),
                order.getTotalPrice());
    }
}
