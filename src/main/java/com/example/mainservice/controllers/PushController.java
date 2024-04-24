package com.example.mainservice.controllers;

import com.example.mainservice.models.entities.Push;
import com.example.mainservice.models.entities.User;
import com.example.mainservice.models.models.requests.PushSendDtoReq;
import com.example.mainservice.models.models.responses.PushDtoRes;
import com.example.mainservice.models.models.responses.UserDtoRes;
import com.example.mainservice.security.CustomUserDetails;
import com.example.mainservice.services.PushService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/push")
@Tag(name = "Push API", description = "")
public class PushController {

    private final PushService pushService;
    @Operation(summary = "Отправить пуш уведомление")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    //@PreAuthorize("isAuthenticated()")
    @PostMapping("")
    public ResponseEntity<PushDtoRes> sendPush(@RequestBody @Valid PushSendDtoReq req,
                                               @AuthenticationPrincipal CustomUserDetails customUserDetails){
        Push push = pushService.createPush(req);
        PushDtoRes res = PushDtoRes.mapFromEntity(push);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
}
