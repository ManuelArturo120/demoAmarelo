package com.demoAmarelo.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "productos")
public class Producto  implements Serializable{
    @Id
    private String id;
    private String nombre;
    private double precio;

    // Getters y Setters
}