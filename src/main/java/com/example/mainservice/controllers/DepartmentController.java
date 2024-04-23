package com.example.mainservice.controllers;

import com.example.mainservice.models.models.requests.CreateDepartmentDtoReq;
import com.example.mainservice.models.models.responses.DepartmentDtoRes;
import com.example.mainservice.services.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/department")
@Tag(name = "Department API", description = "")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Operation(summary = "Создать отдел")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DepartmentDtoRes.class))
                    })
    })
    @PostMapping("")
    public ResponseEntity<DepartmentDtoRes> create(@RequestBody @Valid CreateDepartmentDtoReq req){
        DepartmentDtoRes res = DepartmentDtoRes.mapFromEntity(departmentService.create(req));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Найти отделы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DepartmentDtoRes.class))
                    })
    })
    @GetMapping("")
    public ResponseEntity<Page<DepartmentDtoRes>> getByPage(@PageableDefault Pageable pageable){
        Page<DepartmentDtoRes> res = departmentService.findByPage(pageable).map(DepartmentDtoRes::mapFromEntity);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Найти отдел")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DepartmentDtoRes.class))
                    })
    })
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDtoRes> getById(@PathVariable(name = "id")UUID id){
        DepartmentDtoRes res = DepartmentDtoRes.mapFromEntity(departmentService.findById(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
}
