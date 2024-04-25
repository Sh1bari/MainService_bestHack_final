package com.example.mainservice.controllers;

import com.example.mainservice.models.entities.Push;
import com.example.mainservice.models.entities.User;
import com.example.mainservice.models.enums.UserRoleInDepartment;
import com.example.mainservice.models.models.requests.PushSendDtoReq;
import com.example.mainservice.models.models.responses.PushDtoRes;
import com.example.mainservice.models.models.responses.UserDtoRes;
import com.example.mainservice.security.CustomUserDetails;
import com.example.mainservice.services.PushService;
import com.example.mainservice.specifications.PushSpecificationsBuilder;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Push API", description = "")
public class PushController {

    private final PushService pushService;
    @Operation(summary = "Отправить пуш уведомление")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    //@PreAuthorize("isAuthenticated()")
    @PostMapping("/department/push")
    public ResponseEntity<PushDtoRes> sendPush(@RequestParam(required = false) UUID id,
                                               @RequestBody @Valid PushSendDtoReq req,
                                               @AuthenticationPrincipal CustomUserDetails customUserDetails){
        Push push = pushService.createPush(id, customUserDetails.getUser(), req);
        PushDtoRes res = PushDtoRes.mapFromEntity(push);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Посмотреть историю пушей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    //@PreAuthorize("isAuthenticated()")
    @GetMapping("/pushes")
    public ResponseEntity<Page<PushDtoRes>> getPushes(@RequestParam(required = false) UUID creatorUserId,
                                                      @RequestParam(required = false) UUID fromDepartmentId,
                                                      @RequestParam(required = false) UUID toUserId,
                                                      @PageableDefault Pageable pageable,
                                                      @AuthenticationPrincipal CustomUserDetails customUserDetails){
        Specification<Push> spec = new PushSpecificationsBuilder()
                .byCreatorUserId(creatorUserId)
                .byFromDepartmentId(fromDepartmentId)
                .byToUserId(toUserId)
                .build();
        Page<PushDtoRes> res = pushService.getPushes(spec, pageable).map(PushDtoRes::mapFromEntityWithoutHistory);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Посмотреть пуш")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    //@PreAuthorize("isAuthenticated()")
    @GetMapping("/push/{id}")
    public ResponseEntity<PushDtoRes> getPush(
            @AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable UUID id){
        PushDtoRes res = PushDtoRes.mapFromEntity(pushService.getPush(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
}
