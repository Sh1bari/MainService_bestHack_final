package com.example.mainservice.models.models.responses;

import com.example.mainservice.models.entities.Product;
import jakarta.persistence.Column;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDtoRes {
    private UUID id;
    private String name;
    private Double price;
    private String description;
    public static ProductDtoRes mapFromEntity(Product p){
        ProductDtoRes res = ProductDtoRes.builder()
                .id(p.getId())
                .name(p.getName())
                .price(p.getPrice())
                .description(p.getDescription())
                .build();
        return res;
    }
}
