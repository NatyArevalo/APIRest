package com.store.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.DTO.NewOrderDTO;
import com.store.DTO.OrderDTO;
import com.store.entities.Client;
import com.store.entities.Order;
import com.store.entities.OrderProduct;
import com.store.entities.Product;
import com.store.exceptions.MiException;
import com.store.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public OrderDTO createOrder(NewOrderDTO newOrderDTO) throws MiException{
        Client client = clientService.getClientByID(newOrderDTO.getClientId());
        Order order = new Order();
        order.setClient(client);
        order.setCreatedDate(newOrderDTO.getCreatedDate());
        List<OrderProduct> orderProducts = new ArrayList<>();
        newOrderDTO.getProductQty().forEach((id, qty) -> {
            Product product;
            try {
                product = productService.getProductById(id);
            } catch (MiException e) {
                throw new RuntimeException(e);
            }
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(product);
            orderProduct.setQuantity(qty);
            orderProduct.setOrder(order);
            orderProducts.add(orderProduct);
        });
        order.setOrderProducts(orderProducts);
        order.setTotalPrice(calculateTotalPrice(order));
        return mapper.convertValue(order, OrderDTO.class);
    }
    public double calculateTotalPrice(Order order){
        double totalPrice = 0;
        for (OrderProduct orderProduct : order.getOrderProducts()) {
            totalPrice += orderProduct.getProduct().getPrice() * orderProduct.getQuantity();
        }
        return totalPrice;
    }
}
