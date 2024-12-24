package com.example.serviceproduct.controller;

import com.example.serviceproduct.model.Product;
import com.example.serviceproduct.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    // Requête pour récupérer tous les produits
    @QueryMapping
    public List<Product> products() {
        return productService.getAllProducts();
    }

    // Requête pour récupérer un produit par ID
    @QueryMapping
    public Product productById(String id) {
        return productService.getProductById(id);
    }

    // Mutation pour ajouter un produit
    @MutationMapping
    public Product addProduct(String name, String description, double price, int stock) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        return productService.saveProduct(product);
    }

    // Mutation pour supprimer un produit
    @MutationMapping
    public boolean deleteProduct(String id) {
        productService.deleteProduct(id);
        return true;
    }
}