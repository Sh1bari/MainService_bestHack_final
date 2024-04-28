package com.example.mainservice.models.models.responses;

import com.example.mainservice.models.entities.Product;
import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphTableDataElementDto {
    private ProductDtoRes product;
    private Integer amount;
    private Double totalSpend;

    public GraphTableDataElementDto(Product product, Integer amount, Double totalSpend){
        this.product = ProductDtoRes.mapFromEntity(product);
        this.amount = amount;
        this.totalSpend = totalSpend;
    }
}
