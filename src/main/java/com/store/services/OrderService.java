package com.store.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.DTO.ClientDTO;
import com.store.DTO.OrderDTO;
import com.store.entities.Client;
import com.store.entities.Order;
import com.store.entities.OrderProducts;
import com.store.entities.Product;
import com.store.exceptions.MiException;
import com.store.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ClientService clientService;
    @Autowired
    ProductService productService;
    @Autowired
    ObjectMapper mapper;

    @Transactional
    public OrderDTO createOrder(String clientId, OrderDTO orderDTO) throws MiException{
        Client client = clientService.getClientByID(clientId);
        Order order = null;
        order.setClient(client);
        List<OrderProducts> orderProducts = new ArrayList<>();
        orderDTO.getOrderProductsDTOs().forEach(productDTO -> {
            Product product = productService.getProductById();
        order.setCreatedDate(LocalDate.now());
        orderRepository.save(order);
        return orderDTO;
    }
}
