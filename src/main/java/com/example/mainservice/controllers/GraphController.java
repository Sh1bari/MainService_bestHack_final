package com.example.mainservice.controllers;

import com.example.mainservice.models.entities.Order;
import com.example.mainservice.models.entities.Region;
import com.example.mainservice.models.models.responses.GraphTableDataElementDto;
import com.example.mainservice.services.GraphTableService;
import com.example.mainservice.specifications.OrderGraphSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;
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
@Tag(name = "Graph API", description = "")
public class GraphController {

    private final GraphTableService graphTableService;

    @Operation(summary = "Получить данные для графика-таблицы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    //TODO для админов
    @GetMapping("/graph/products")
    public ResponseEntity<List<GraphTableDataElementDto>> getTableData(@RequestParam(required = false)@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SS")LocalDateTime startTime,
                                                                       @RequestParam(required = false)@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SS")LocalDateTime endTime,
                                                                       @RequestParam(required = false)String region){
        Specification<Order> spec = OrderGraphSpecification.orderTimeBetween(endTime, startTime);
        List<GraphTableDataElementDto> res = graphTableService.getGraphTableDataElements(startTime, endTime, region);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }


}
