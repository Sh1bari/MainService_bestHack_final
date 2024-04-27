package com.example.mainservice.models.models.responses;

import com.example.mainservice.models.entities.Product;
import com.example.mainservice.models.entities.ProductOrder;
import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderDtoRes {
    private ProductDtoRes product;
    private Long amount;
    private Double totalPrice;

    public static ProductOrderDtoRes mapFromEntity(ProductOrder pr){
        ProductOrderDtoRes res = ProductOrderDtoRes.builder()
                .product(ProductDtoRes.mapFromEntity(pr.getProduct()))
                .amount(pr.getAmount())
                .totalPrice(pr.getAmount() * pr.getProduct().getPrice())
                .build();
        return res;
    }
}
