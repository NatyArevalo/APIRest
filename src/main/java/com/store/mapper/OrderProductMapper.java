package com.store.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.DTO.OrderProductsDTO;
import com.store.DTO.ProductDTO;
import com.store.entities.OrderProduct;
import org.springframework.stereotype.Component;

@Component
public class OrderProductMapper {
    ObjectMapper mapper;

    public OrderProductMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public OrderProductsDTO mapToDTO(OrderProduct orderProduct){
        return new OrderProductsDTO(orderProduct.getOrder().getOrderId(),
                mapper.convertValue(orderProduct.getProduct(), ProductDTO.class),
                orderProduct.getQuantity());
    }
}
