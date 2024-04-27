package com.example.mainservice.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;
    @Column(unique = true)
    private String name;
    private Double price;
    private String description;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<ProductOrder> productOrders;

    public Product(String name, Double price, String description){
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
