package com.example.mainservice.models.models.requests;

import com.example.mainservice.models.enums.UserRoleInDepartment;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendToDepartmentRolesDtoReq {
    private UUID departmentId;
    private List<UserRoleInDepartment> roles;
}
