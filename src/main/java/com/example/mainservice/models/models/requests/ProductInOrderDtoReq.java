package com.example.mainservice.models.models.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInOrderDtoReq {
    @NotNull(message = "productId cant be blank")
    private UUID productId;
    @Min(value = 1, message = "amount cant be less than 1")
    private Integer amount;
}
