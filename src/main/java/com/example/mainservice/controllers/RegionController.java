package com.example.mainservice.controllers;

import com.example.mainservice.models.entities.Region;
import com.example.mainservice.models.entities.User;
import com.example.mainservice.models.models.requests.CreateUserDto;
import com.example.mainservice.repositories.RegionRepo;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("")
@Tag(name = "Region API", description = "")
public class RegionController {

    private final RegionRepo regionRepo;
    @Operation(summary = "Получить список регионов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @GetMapping("/regions")
    public ResponseEntity<List<String>> getRegions(@RequestParam(required = false, defaultValue = "") String region){
        List<String> res = regionRepo.findAllByNameContainsIgnoreCase(region).stream()
                .map(Region::getName).toList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
}
