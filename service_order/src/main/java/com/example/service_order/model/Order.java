package com.example.service_order.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID unique de la commande.

    private String productId; // Référence à l'ID du produit (MongoDB).

    private int quantity; // Quantité commandée.

    private Long customerId; // Référence au client (service-customer).

    private double totalPrice; // Prix total de la commande.
}