package com.example.serviceproduct.repo;

import com.example.serviceproduct.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
