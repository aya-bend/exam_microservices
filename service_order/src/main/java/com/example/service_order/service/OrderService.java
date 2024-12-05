package com.example.service_order.service;

import com.example.service_order.model.Order;
import com.example.service_order.repo.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Obtenir toutes les commandes
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Obtenir une commande par ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));
    }

    // Créer une nouvelle commande
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    // Supprimer une commande par ID
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}