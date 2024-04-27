package com.example.mainservice.controllers;

import com.example.mainservice.models.entities.User;
import com.example.mainservice.models.enums.OrderStatus;
import com.example.mainservice.models.models.requests.CreateUserDto;
import com.example.mainservice.models.models.requests.UpdateUserDtoReq;
import com.example.mainservice.models.models.responses.OrderDtoRes;
import com.example.mainservice.models.models.responses.UserDtoRes;
import com.example.mainservice.repositories.OrderRepo;
import com.example.mainservice.security.CustomUserDetails;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("")
@Tag(name = "User API", description = "")
public class UserController {

    private final UserService userService;
    private final OrderRepo orderRepo;

    @Operation(summary = "Получить себя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @GetMapping("/auth/user/me")
    public ResponseEntity<UserDtoRes> getMe(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        User user = customUserDetails.getUser();
        UserDtoRes res = UserDtoRes.mapFromEntity(user);
        res.setBag(OrderDtoRes.mapFromEntity(orderRepo.findByUserAndOrderStatus(user, OrderStatus.DRAFT)));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Обновить профиль")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @PutMapping("/auth/user/me")
    public ResponseEntity<UserDtoRes> updateMe(@RequestBody UpdateUserDtoReq req, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        User user = customUserDetails.getUser();
        UserDtoRes res = UserDtoRes.mapFromEntity(userService.updateMe(user, req));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

}
