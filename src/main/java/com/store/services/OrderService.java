package com.store.services;

import com.store.DTO.*;
import com.store.entities.Client;
import com.store.entities.Order;
import com.store.entities.OrderProduct;
import com.store.entities.Product;
import com.store.exceptions.MiException;
import com.store.mapper.OrderMapper;
import com.store.mapper.OrderProductMapper;
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


    private final OrderProductMapper orderProductMapper;

    private final OrderMapper orderMapper;

    public OrderService(OrderProductMapper orderProductMapper, OrderMapper orderMapper) {
        this.orderProductMapper = orderProductMapper;
        this.orderMapper = orderMapper;
    }

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
            OrderProductsDTO orderProductDTO;
            orderProduct.setProduct(product);
            orderProduct.setQuantity(qty);
            orderProduct.setOrder(order);
            orderProducts.add(orderProduct);
            orderProductDTO = orderProductMapper.mapToDTO(orderProduct);
            orderProductsDTO.add(orderProductDTO);
        });
        order.setOrderProducts(orderProducts);
        order.setTotalPrice(calculateTotalPrice(order));
        OrderDTO orderDTO;
        orderDTO = orderMapper.maptoDTO(order) ;
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
    public OrderDTO modifyById(java.lang.Long orderId, NewOrderDTO newOrderDTO) throws MiException{
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
                OrderProductsDTO orderProductDTO;
                orderProduct.setProduct(product);
                orderProduct.setQuantity(qty);
                orderProduct.setOrder(order);
                orderProducts.add(orderProduct);
                orderProductDTO = orderProductMapper.mapToDTO(orderProduct);
                orderProductsDTO.add(orderProductDTO);

            });
            order.setOrderProducts(orderProducts);
            order.setTotalPrice(calculateTotalPrice(order));
            orderDTO = orderMapper.maptoDTO(order);
            orderDTO.setTotalPrice(order.getTotalPrice());
        }
        return orderDTO;
    }
    @Transactional
    public void deleteOrder(java.lang.Long id) throws MiException{
        if(id <= 0){
            throw new MiException("id needs to be valid");
        }
        orderRepository.deleteById(id);
    }
    public List<OrderDTO> getOrdersList(){
        List<Order> orders = (ArrayList<Order>) orderRepository.findAll();
        List<OrderDTO> ordersDTO = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO;
            orderDTO = orderMapper.maptoDTO(order) ;
            ordersDTO.add(orderDTO);
        }
        return ordersDTO;
    }
    public Object getOrderById(java.lang.Long id) throws MiException{
        if(id <= 0){
            throw new MiException("id needs to be valid");
        }
        return orderRepository.findById(id);
    }
}
