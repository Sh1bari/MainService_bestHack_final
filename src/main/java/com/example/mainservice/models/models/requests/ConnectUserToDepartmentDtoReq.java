package com.example.mainservice.models.models.requests;

import com.example.mainservice.models.enums.UserRoleInDepartment;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectUserToDepartmentDtoReq {
    @NotEmpty(message = "roles cant be empty")
    private List<UserRoleInDepartment> roles;
}
