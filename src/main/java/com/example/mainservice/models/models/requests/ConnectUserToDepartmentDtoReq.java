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
    private List<UserRoleInDepartment> roles;
}
