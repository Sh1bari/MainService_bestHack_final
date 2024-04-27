package com.example.mainservice.models.models.responses;

import com.example.mainservice.models.entities.Order;
import com.example.mainservice.models.entities.ProductOrder;
import com.example.mainservice.models.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDtoRes {
    private Long id;
    private LocalDateTime orderTime;
    private List<ProductOrderDtoRes> productOrders;

    public static OrderDtoRes mapFromEntity(Order o){
        OrderDtoRes res = OrderDtoRes.builder()
                .id(o.getId())
                .orderTime(o.getOrderTime())
                .productOrders(o.getProductOrders().stream().map(ProductOrderDtoRes::mapFromEntity).toList())
                .build();
        return res;
    }
}
