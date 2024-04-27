package com.example.mainservice.controllers;

import com.example.mainservice.migrations.Migration;
import com.example.mainservice.models.entities.User;
import com.example.mainservice.models.models.requests.CreateUserDto;
import com.example.mainservice.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("")
@Tag(name = "Service API", description = "")
public class ServiceController {
    private final UserService userService;
    private final Migration migration;

    @Operation(summary = "Service method")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @PostMapping("/auth/user")
    public ResponseEntity<?> createNewUser(@RequestBody @Valid CreateUserDto req){
        User user = userService.createNewUserByAuthService(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Service method")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @PostMapping("/imitate")
    public void stop(@RequestParam Boolean bool){
        migration.inited = bool;
    }
}
