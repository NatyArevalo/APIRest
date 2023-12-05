package com.store.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDate;
import java.util.List;

public class OrderDTO {
    private ClientDTO clientDTO;
    private LocalDate createdDate;
    @JsonManagedReference
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

    public void setClientDTO(ClientDTO clientDTO) {
        this.clientDTO = clientDTO;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public void setOrderProductsDTOs(List<OrderProductsDTO> orderProductsDTOs) {
        this.orderProductsDTOs = orderProductsDTOs;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
