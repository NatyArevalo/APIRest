package com.store.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class OrderProductsDTO {
    private Long orderId;
    private ProductDTO productDTO;
    private int qty;

    public OrderProductsDTO() {
    }

    public OrderProductsDTO(Long orderId, ProductDTO productDTO, int qty) {
        this.orderId = orderId;
        this.productDTO = productDTO;
        this.qty = qty;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public int getQty() {
        return qty;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
