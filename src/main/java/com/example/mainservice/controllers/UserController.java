package com.example.mainservice.controllers;

import com.example.mainservice.models.entities.Department;
import com.example.mainservice.models.entities.User;
import com.example.mainservice.models.enums.UserRoleInDepartment;
import com.example.mainservice.models.models.responses.UserDtoRes;
import com.example.mainservice.security.CustomUserDetails;
import com.example.mainservice.services.DepartmentService;
import com.example.mainservice.services.UserService;
import com.example.mainservice.specifications.UserSpecificationsBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User API", description = "")
public class UserController {
    private final UserService userService;
    @Operation(summary = "Список пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    //@PreAuthorize("isAuthenticated()")
    @GetMapping("/user")
    public ResponseEntity<Page<UserDtoRes>> getUserPage(@RequestParam(required = false) Boolean hasDepartment,
                                                        @RequestParam(required = false) UUID departmentId,
                                                        @RequestParam(required = false) UserRoleInDepartment role,
                                                        @RequestParam(required = false) String name,
                                                        @RequestParam(required = false) String middleName,
                                                        @RequestParam(required = false) String surname,
                                                        @RequestParam(required = false) String username,
                                                        @PageableDefault Pageable pageable){
        Specification<User> spec = new UserSpecificationsBuilder()
                .withNameContains(name)
                .withMiddleNameContains(middleName)
                .withSurnameContains(surname)
                .withUsernameContains(username)
                .hasRole(role)
                .hasDepartment(hasDepartment)
                .withDepartmentIdEquals(departmentId)
                .build();
        Page<User> users = userService.getUserPage(spec, pageable);
        Page<UserDtoRes> res = users.map(UserDtoRes::mapFromEntityWithoutCanSendTo);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    private final DepartmentService departmentService;
    @Operation(summary = "Список пользователей которым можно отправить")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    //@PreAuthorize("isAuthenticated()")
    @GetMapping("/department/{id}/canSendUsers")
    public ResponseEntity<Page<UserDtoRes>> getUserPageCanSend(@RequestParam(required = false) Boolean hasDepartment,
                                                               @RequestParam(required = false) UserRoleInDepartment role,
                                                               @RequestParam(required = false) String name,
                                                               @RequestParam(required = false) String middleName,
                                                               @RequestParam(required = false) String surname,
                                                               @RequestParam(required = false) String username,
                                                               @PageableDefault Pageable pageable,
                                                               @PathVariable UUID id,
                                                               @AuthenticationPrincipal CustomUserDetails customUserDetails){
        Specification<User> spec = new UserSpecificationsBuilder()
                .withNameContains(name)
                .withMiddleNameContains(middleName)
                .withSurnameContains(surname)
                .withUsernameContains(username)
                .hasRole(role)
                .hasDepartment(hasDepartment)
                .build();
        Department department = departmentService.findById(id);
        Page<User> users = userService.getUserPageCanSend(customUserDetails.getUser(),
                department, spec, pageable);
        Page<UserDtoRes> res = users.map(UserDtoRes::mapFromEntityWithoutCanSendTo);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
}
