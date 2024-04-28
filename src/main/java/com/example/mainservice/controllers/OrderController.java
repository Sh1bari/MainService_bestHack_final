package com.example.mainservice.controllers;

import com.example.mainservice.models.entities.Order;
import com.example.mainservice.models.models.requests.ProductInOrderDtoReq;
import com.example.mainservice.models.models.responses.OrderDtoRes;
import com.example.mainservice.models.models.responses.ProductDtoRes;
import com.example.mainservice.security.CustomUserDetails;
import com.example.mainservice.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("")
@Tag(name = "Order API", description = "")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Добавить в заказ товары")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @Secured("ROLE_USER")
    @PutMapping("/order")
    public ResponseEntity<OrderDtoRes> addProductToOrder(@RequestBody @Valid List<ProductInOrderDtoReq> req,
                                                         @AuthenticationPrincipal CustomUserDetails customUserDetails){
        Order order = orderService.putProductsToOrder(customUserDetails.getUser(), req);
        OrderDtoRes res = OrderDtoRes.mapFromEntity(order);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Оформить покупку")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Возвращаю айди новой корзины")
    })
    @Secured("ROLE_USER")
    @PostMapping("/order/complete")
    public ResponseEntity<OrderDtoRes> completeOrder(@RequestParam String region,
                                                     @AuthenticationPrincipal CustomUserDetails customUserDetails){
        Order order = orderService.completeOrder(customUserDetails.getUser(), region);
        OrderDtoRes res = OrderDtoRes.mapFromEntity(order);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Своя история покупок")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Возвращаю айди новой корзины")
    })
    @Secured("ROLE_USER")
    @GetMapping("/orders")
    public ResponseEntity<Page<OrderDtoRes>> completeOrder(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                           @PageableDefault Pageable pageable){
        Page<OrderDtoRes> res = orderService.getSelfOrderList(customUserDetails.getUser(), pageable)
                .map(OrderDtoRes::mapFromEntity);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
}
