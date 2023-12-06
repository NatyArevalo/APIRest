package com.store.controllers;

import com.store.DTO.NewOrderDTO;
import com.store.DTO.OrderDTO;
import com.store.entities.Order;
import com.store.exceptions.MiException;
import com.store.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderService;
    @GetMapping("/")
    public List<OrderDTO> getOrders(){
        return orderService.getOrdersList();
    }
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable java.lang.Long id) throws MiException {
        return (Order) orderService.getOrderById(id);
    }
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@RequestBody NewOrderDTO newOrderDTO) throws MiException {
        return orderService.createOrder(newOrderDTO);
    }
    @PutMapping("/modify/{id}")
    public OrderDTO modifyById(@PathVariable java.lang.Long id, @RequestBody NewOrderDTO newOrderDTO) throws MiException {
        return orderService.modifyById(id, newOrderDTO);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable java.lang.Long id) throws MiException {
        orderService.deleteOrder(id);
    }
}
