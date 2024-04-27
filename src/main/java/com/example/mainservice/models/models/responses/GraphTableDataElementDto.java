package com.example.mainservice.models.models.responses;

import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphTableDataElementDto {
    private ProductDtoRes product;
    private Long amount;
    private Double totalSpend;
}
