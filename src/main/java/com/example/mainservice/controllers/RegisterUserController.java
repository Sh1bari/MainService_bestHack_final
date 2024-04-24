package com.example.mainservice.controllers;

import com.example.mainservice.models.entities.User;
import com.example.mainservice.models.models.requests.CreateUserDto;
import com.example.mainservice.models.models.requests.UpdateUserDtoReq;
import com.example.mainservice.models.models.responses.UserDtoRes;
import com.example.mainservice.security.CustomUserDetails;
import com.example.mainservice.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("")
@Tag(name = "Authorization API", description = "")
public class RegisterUserController {
    private final UserService userService;

    @Operation(summary = "Service method")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"/*,
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JwtTokenDtoRes.class))
                    }*/)
    })
    @PostMapping("/auth/user")
    public ResponseEntity<?> createNewUser(@RequestBody @Valid CreateUserDto req){
        User user = userService.createNewUser(req);
        //UserDtoRes res = UserDtoRes.mapFromEntity(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Привязать push токен")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/auth/user/token")
    public ResponseEntity<UserDtoRes> linkPushToken(@RequestParam(name = "token") String token,
                                           @AuthenticationPrincipal CustomUserDetails customUserDetails){
        User user = userService.linkTokenToUser(customUserDetails.getUser(), token);
        UserDtoRes res = UserDtoRes.mapFromEntity(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Получить себя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/auth/user/me")
    public ResponseEntity<UserDtoRes> getMe(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        User user = customUserDetails.getUser();
        UserDtoRes res = UserDtoRes.mapFromEntity(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Изменить информацию о себе / о пользователе (если админ)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/auth/user")
    public ResponseEntity<UserDtoRes> updateUser(@RequestParam(required = false) UUID userId,
                                                 @RequestBody UpdateUserDtoReq req,
                                                 @AuthenticationPrincipal CustomUserDetails customUserDetails){
        UserDtoRes res = UserDtoRes.mapFromEntity(userService.updateUserInfo(userId!=null? userId : customUserDetails.getUser().getId(), req));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

}
