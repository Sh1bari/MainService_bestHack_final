package com.example.mainservice.models.models;

import com.example.mainservice.models.entities.ProductOrder;
import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SumResult {
    private Long totalAmount = 0L;
    private Double totalSpend = 0D;
    public void accumulate(ProductOrder order) {
        totalAmount += order.getAmount();
        totalSpend += order.getTotalPrice();
    }

    public void combine(SumResult other) {
        totalAmount += other.getTotalAmount();
        totalSpend += other.getTotalSpend();
    }
}
