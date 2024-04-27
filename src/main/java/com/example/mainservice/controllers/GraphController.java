package com.example.mainservice.controllers;

import com.example.mainservice.models.entities.Region;
import com.example.mainservice.models.models.responses.GraphTableDataElementDto;
import com.example.mainservice.services.GraphTableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/graph/products")
    public ResponseEntity<List<GraphTableDataElementDto>> getTableData(){
        List<GraphTableDataElementDto> res = graphTableService.getGraphTableDataElements();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }


}
