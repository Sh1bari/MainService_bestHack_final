package com.example.mainservice.controllers;

import com.example.mainservice.models.entities.Order;
import com.example.mainservice.models.models.responses.GraphTableDataElementDto;
import com.example.mainservice.models.models.responses.ProductDtoRes;
import com.example.mainservice.services.ProductService;
import com.example.mainservice.specifications.OrderGraphSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("")
@Tag(name = "Product API", description = "")
public class ProductController {
    private final ProductService productService;
    @Operation(summary = "Получить продукты")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @GetMapping("/products")
    public ResponseEntity<List<ProductDtoRes>> getProducts(@RequestParam(required = false, defaultValue = "")String pattern,
                                                           @PageableDefault Pageable pageable){
        List<ProductDtoRes> res = productService.findAllBySearch(pattern, pageable).stream()
                .map(ProductDtoRes::mapFromEntity).toList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
}
