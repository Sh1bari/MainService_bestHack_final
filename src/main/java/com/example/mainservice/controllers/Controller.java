package com.example.mainservice.controllers;

import com.example.mainservice.feign.AuthClient;
import com.example.mainservice.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Vladimir Krasnov
 */
@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("")
@Tag(name = "Authorization API", description = "")
public class Controller {

    private final AuthClient authClient;

    @GetMapping("/test/401")
    public ResponseEntity<?> fo(){
        return ResponseEntity
                .status(HttpStatusCode.valueOf(401))
                .build();
    }
    @GetMapping("/test/403")
    public ResponseEntity<?> ft(){
        return ResponseEntity
                .status(HttpStatusCode.valueOf(403))
                .build();
    }

    @GetMapping("/test/503")
    public ResponseEntity<?> fon(){
        return ResponseEntity
                .status(HttpStatusCode.valueOf(503))
                .build();
    }
}
