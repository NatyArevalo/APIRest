package com.store.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.DTO.*;
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
import java.util.Optional;

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
        List<OrderProductsDTO> orderProductsDTO = new ArrayList<>();
        newOrderDTO.getProductQty().forEach((id, qty) -> {
            Product product;
            try {
                product = productService.getProductById(id);
            } catch (MiException e) {
                throw new RuntimeException(e);
            }
            OrderProduct orderProduct = new OrderProduct();
            OrderProductsDTO orderProductDTO = new OrderProductsDTO();
            orderProduct.setProduct(product);
            orderProduct.setQuantity(qty);
            orderProduct.setOrder(order);
            orderProducts.add(orderProduct);
            orderProductDTO.setProductDTO(mapper.convertValue(product, ProductDTO.class));
            orderProductDTO.setOrder(order);
            orderProductDTO.setQty(qty);
            orderProductsDTO.add(orderProductDTO);
        });
        order.setOrderProducts(orderProducts);
        order.setTotalPrice(calculateTotalPrice(order));
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setClientDTO(mapper.convertValue(client, ClientDTO.class));
        orderDTO.setCreatedDate(order.getCreatedDate());
        orderDTO.setOrderProductsDTOs(orderProductsDTO);
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderRepository.save(order);
        return orderDTO;
    }
    public double calculateTotalPrice(Order order){
        double totalPrice = 0;
        for (OrderProduct orderProduct : order.getOrderProducts()) {
            totalPrice += orderProduct.getProduct().getPrice() * orderProduct.getQuantity();
        }
        return totalPrice;
    }
    @Transactional
    public OrderDTO modifyById(Long orderId, NewOrderDTO newOrderDTO) throws MiException{
        Optional<Order> response = orderRepository.findById(orderId);
        OrderDTO orderDTO = new OrderDTO();
        if(response.isPresent()){
           Order order = response.get();
            List<OrderProduct> orderProducts = new ArrayList<>();
            List<OrderProductsDTO> orderProductsDTO = new ArrayList<>();
            newOrderDTO.getProductQty().forEach((id, qty) -> {
                Product product;
                try {
                    product = productService.getProductById(id);
                } catch (MiException e) {
                    throw new RuntimeException(e);
                }
                OrderProduct orderProduct = new OrderProduct();
                OrderProductsDTO orderProductDTO = new OrderProductsDTO();
                orderProduct.setProduct(product);
                orderProduct.setQuantity(qty);
                orderProduct.setOrder(order);
                orderProducts.add(orderProduct);
                orderProductDTO.setProductDTO(mapper.convertValue(product, ProductDTO.class));
                orderProductDTO.setOrder(order);
                orderProductDTO.setQty(qty);
                orderProductsDTO.add(orderProductDTO);

            });
            order.setOrderProducts(orderProducts);
            order.setTotalPrice(calculateTotalPrice(order));
            orderDTO.setClientDTO(mapper.convertValue(order.getClient(), ClientDTO.class));
            orderDTO.setCreatedDate(order.getCreatedDate());
            orderDTO.setOrderProductsDTOs(orderProductsDTO);
            orderDTO.setTotalPrice(order.getTotalPrice());
        }
        return orderDTO;
    }
    @Transactional
    public void deleteOrder(Long id) throws MiException{
        if(id <= 0){
            throw new MiException("id needs to be valid");
        }
        orderRepository.deleteById(id);
    }
    public List<Order> getOrdersList(){
        return (ArrayList<Order>) orderRepository.findAll();
    }
    public Object getOrderById(Long id) throws MiException{
        if(id <= 0){
            throw new MiException("id needs to be valid");
        }
        return orderRepository.findById(id);
    }
}
