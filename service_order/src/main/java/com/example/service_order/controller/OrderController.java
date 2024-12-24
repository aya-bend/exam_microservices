package com.example.service_order.controller;

import com.example.service_order.model.Order;
import com.example.service_order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @QueryMapping
    public List<Order> orders() {
        return orderService.getAllOrders();
    }

    @QueryMapping
    public Order orderById(Long id) {
        return orderService.getOrderById(id);
    }

    @MutationMapping
    public Order createOrder(String productId, int quantity, Long customerId, double totalPrice) {
        Order order = new Order();
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setCustomerId(customerId);
        order.setTotalPrice(totalPrice);
        return orderService.createOrder(order);
    }

    @MutationMapping
    public boolean deleteOrder(Long id) {
        orderService.deleteOrder(id);
        return true;
    }
}