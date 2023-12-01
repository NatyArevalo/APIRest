package com.store.controllers;

import com.store.DTO.NewOrderDTO;
import com.store.DTO.OrderDTO;
import com.store.exceptions.MiException;
import com.store.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderService;
//    @GetMapping("/{id}")
//    public OrderDTO getOrderById(@PathVariable Long id){
//
//    }
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@RequestBody NewOrderDTO newOrderDTO) throws MiException {
        return orderService.createOrder(newOrderDTO);
    }
}
