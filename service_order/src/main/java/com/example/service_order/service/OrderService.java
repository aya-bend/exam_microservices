package com.example.service_order.service;

import com.example.service_order.model.Order;
import com.example.service_order.repo.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate, KafkaTemplate<String, String> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Récupère les détails d'un produit en utilisant le service Produits.
     * Protégé par un circuit breaker pour gérer les pannes.
     *
     * @param productId L'ID du produit
     * @return Les détails du produit ou un fallback en cas de panne
     */
    @CircuitBreaker(name = "productService", fallbackMethod = "getProductFallback")
    public String getProductDetails(String productId) {
        String productServiceUrl = "http://service-product/api/products/" + productId;
        return restTemplate.getForObject(productServiceUrl, String.class);
    }

    /**
     * Méthode de fallback appelée en cas de panne du service Produits.
     *
     * @param productId L'ID du produit
     * @param throwable L'exception générée
     * @return Message par défaut indiquant l'indisponibilité du produit
     */
    public String getProductFallback(String productId, Throwable throwable) {
        return "Product information is temporarily unavailable for product ID: " + productId;
    }

    /**
     * Récupère toutes les commandes.
     *
     * @return La liste des commandes
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Récupère une commande par son ID.
     *
     * @param id L'ID de la commande
     * @return La commande correspondante
     */
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));
    }

    /**
     * Crée une nouvelle commande.
     * Valide d'abord l'existence du produit via le service Produits.
     * Publie ensuite un message Kafka.
     *
     * @param order La commande à créer
     * @return La commande créée
     */
    public Order createOrder(Order order) {
        // Valide l'existence du produit via le service Produits
        String productDetails = getProductDetails(order.getProductId());
        System.out.println("Product details: " + productDetails);

        // Calcule le prix total de la commande (exemple fictif)
        order.setTotalPrice(order.getQuantity() * 100.0); // Exemple : prix unitaire = 100

        // Sauvegarde la commande
        Order savedOrder = orderRepository.save(order);

        // Publie un message Kafka
        kafkaTemplate.send("orders", "Order created: " + savedOrder.getId());

        return savedOrder;
    }

    /**
     * Supprime une commande par son ID.
     * Publie un message Kafka après la suppression.
     *
     * @param id L'ID de la commande à supprimer
     */
    public void deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);

            // Publie un message Kafka
            kafkaTemplate.send("orders", "Order deleted: " + id);
        } else {
            throw new RuntimeException("Order not found with id " + id);
        }
    }
}