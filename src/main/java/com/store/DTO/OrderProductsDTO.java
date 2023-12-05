package com.store.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.store.entities.Order;

public class OrderProductsDTO {
    @JsonBackReference
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

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
