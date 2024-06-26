package com.example.mainservice.controllers;

import com.example.mainservice.models.entities.Department;
import com.example.mainservice.models.entities.User;
import com.example.mainservice.models.models.requests.ConnectUserToDepartmentDtoReq;
import com.example.mainservice.models.models.requests.CreateDepartmentDtoReq;
import com.example.mainservice.models.models.responses.DepartmentDtoRes;
import com.example.mainservice.models.models.responses.UserDtoRes;
import com.example.mainservice.services.DepartmentService;
import com.example.mainservice.services.UserService;
import com.example.mainservice.specifications.DepartmentSpecificationsBuilder;
import com.example.mainservice.specifications.UserSpecificationsBuilder;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    private final UserService userService;

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
    @Operation(summary = "Изменить отдел")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DepartmentDtoRes.class))
                    })
    })
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDtoRes> update(@PathVariable(name = "id")UUID id,
                                                   @RequestBody @Valid CreateDepartmentDtoReq req){
        DepartmentDtoRes res = DepartmentDtoRes.mapFromEntity(departmentService.update(req, id));
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
    public ResponseEntity<Page<DepartmentDtoRes>> getByPage(@RequestParam(required = false) String name,
                                                            @PageableDefault Pageable pageable){
        Specification<Department> spec = new DepartmentSpecificationsBuilder()
                .withNameContains(name)
                .build();
        Page<DepartmentDtoRes> res = departmentService.findByPage(spec, pageable).map(DepartmentDtoRes::mapFromEntity);
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

    @Operation(summary = "Привязать пользователя к отделу / изменить права")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @PostMapping("/{id}/user")
    public ResponseEntity<DepartmentDtoRes> connectUserToDepartment(@PathVariable(name = "id")UUID id,
                                                                    @RequestParam(name = "userId") UUID userId,
                                                                    @RequestBody @Valid ConnectUserToDepartmentDtoReq req){
        DepartmentDtoRes res = DepartmentDtoRes.mapFromEntity(departmentService.connectUserToDepartment(id, userId, req));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Заменить список разрешенных отделов для отправки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @PutMapping("/{id}/can-send-to")
    public ResponseEntity<DepartmentDtoRes> updateCanSendTo(@PathVariable(name = "id")UUID id,
                                                            @RequestBody List<UUID> req){
        DepartmentDtoRes res = DepartmentDtoRes.mapFromEntity(departmentService.updateCanSendTo(id,req));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Список пользователей в отделе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @GetMapping("/{id}/users")
    public ResponseEntity<Page<UserDtoRes>> getUsersByDepartment(@PathVariable(name = "id")UUID id,
                                                                 @PageableDefault Pageable pageable){
        Page<UserDtoRes> res = userService.getUsersByDepartmentId(id, pageable)
                .map(UserDtoRes::mapFromEntityWithoutCanSendTo);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }


}
