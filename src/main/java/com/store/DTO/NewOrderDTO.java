package com.store.DTO;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class NewOrderDTO {
    private LocalDate createdDate;
    private String clientId;
    private Map<Long, Integer> productQty = new HashMap<>();

    public NewOrderDTO(String clientId, Map<Long, Integer> productQty) {
        this.clientId = clientId;
        this.productQty = productQty;
    }

    public String getClientId() {
        return clientId;
    }

    public Map<Long, Integer> getProductQty() {
        return productQty;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }
}
