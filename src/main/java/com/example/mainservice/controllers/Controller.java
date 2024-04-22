package com.example.mainservice.controllers;

import com.example.mainservice.feign.AuthClient;
import com.example.mainservice.services.FCMService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final FCMService fcmService;

    @GetMapping("/test")
    public String generateToken(){
        return "test";
    }
    @PostMapping("/send")
    public void generateToken(@RequestParam String token){
        fcmService.sendNotification(token, "qwe", "qwe");
    }
}
