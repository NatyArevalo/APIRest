package com.store.DTO;

import com.store.entities.Order;

public class OrderProductsDTO {
    private Order order;
    private ProductDTO productDTO;
    private int qty;

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public int getQty() {
        return qty;
    }

    public Order getOrder() {
        return order;
    }
}
