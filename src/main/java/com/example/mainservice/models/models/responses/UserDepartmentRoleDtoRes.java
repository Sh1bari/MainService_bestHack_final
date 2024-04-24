package com.example.mainservice.models.models.responses;

import com.example.mainservice.models.entities.UserDepartmentRole;
import com.example.mainservice.models.enums.UserRoleInDepartment;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDepartmentRoleDtoRes {
    private UserDtoRes user;
    private DepartmentDtoRes department;
    private List<UserRoleInDepartment> userRole;

    public static UserDepartmentRoleDtoRes mapFromEntity(UserDepartmentRole u){
        UserDepartmentRoleDtoRes res = UserDepartmentRoleDtoRes.builder()
                .user(UserDtoRes.mapFromEntity(u.getUser()))
                .department(DepartmentDtoRes.mapFromEntity(u.getDepartment()))
                .userRole(u.getRoles())
                .build();
        return res;
    }

    public static UserDepartmentRoleDtoRes mapFromEntityWithoutUser(UserDepartmentRole u){
        UserDepartmentRoleDtoRes res = UserDepartmentRoleDtoRes.builder()
                .department(DepartmentDtoRes.mapFromEntity(u.getDepartment()))
                .userRole(u.getRoles())
                .build();
        return res;
    }
}
