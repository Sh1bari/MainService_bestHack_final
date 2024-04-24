package com.example.mainservice.models.models.responses;

import com.example.mainservice.models.entities.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDtoRes {
    private UUID id;
    private String name;
    private String middleName;
    private String surname;
    private String username;
    private LocalDateTime createDate;
    private List<UserDepartmentRoleDtoRes> departmentRoles;

    public static UserDtoRes mapFromEntity(User u){
        UserDtoRes res = UserDtoRes.builder()
                .id(u.getId())
                .name(u.getName())
                .middleName(u.getMiddleName())
                .surname(u.getSurname())
                .createDate(u.getCreateDate())
                .username(u.getUsername())
                .departmentRoles(u.getDepartmentRoles().stream()
                                .map(o->{
                                    UserDepartmentRoleDtoRes udr = UserDepartmentRoleDtoRes.mapFromEntityWithoutUser(o);
                                    return udr;
                                })
                        .toList()
                )
                .build();
        return res;
    }
    public static UserDtoRes mapFromEntityWithoutCanSendTo(User u){
        UserDtoRes res = UserDtoRes.builder()
                .id(u.getId())
                .name(u.getName())
                .middleName(u.getMiddleName())
                .surname(u.getSurname())
                .createDate(u.getCreateDate())
                .username(u.getUsername())
                .departmentRoles(u.getDepartmentRoles().stream()
                        .map(o->{
                            UserDepartmentRoleDtoRes udr = UserDepartmentRoleDtoRes.mapFromEntityWithoutUser(o);
                            udr.getDepartment().setCanSendTo(null);
                            return udr;
                        })
                        .toList()
                )
                .build();
        return res;
    }
}
