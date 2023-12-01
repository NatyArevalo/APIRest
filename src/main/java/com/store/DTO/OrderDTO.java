package com.store.DTO;

import java.time.LocalDate;
import java.util.List;

public class OrderDTO {
    private ClientDTO clientDTO;
    private LocalDate createdDate;
    private List<OrderProductsDTO> orderProductsDTOs;
    private double totalPrice;

    public ClientDTO getClientDTO() {
        return clientDTO;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public List<OrderProductsDTO> getOrderProductsDTOs() {
        return orderProductsDTOs;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
