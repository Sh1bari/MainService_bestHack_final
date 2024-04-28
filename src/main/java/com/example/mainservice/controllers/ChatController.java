package com.example.mainservice.controllers;

import com.example.mainservice.models.entities.Order;
import com.example.mainservice.models.models.requests.MessageDto;
import com.example.mainservice.models.models.responses.OrderDtoRes;
import com.example.mainservice.security.CustomUserDetails;
import com.example.mainservice.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("")
@Tag(name = "Chat API", description = "")
public class ChatController {

    private final MessageService messageService;
    @Operation(summary = "Оформить покупку")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Возвращаю айди новой корзины")
    })
    @Secured("ROLE_USER")
    @PostMapping("/chat/message")
    public ResponseEntity<MessageDto> sendMessage(@RequestBody MessageDto message,
                                         @AuthenticationPrincipal CustomUserDetails customUserDetails){
        MessageDto res = messageService.sendMessage(message);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
}
