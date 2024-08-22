package com.demoAmarelo.repository;



import org.springframework.data.mongodb.repository.MongoRepository;

import com.demoAmarelo.model.Producto;

public interface ProductoRepository extends MongoRepository<Producto, String> {
}
