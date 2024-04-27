package com.example.mainservice.models.models.responses;

import com.example.mainservice.models.entities.Role;
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
    private List<String> roles;

    public static UserDtoRes mapFromEntity(User u){
        UserDtoRes res = UserDtoRes.builder()
                .id(u.getId())
                .name(u.getName())
                .middleName(u.getMiddleName())
                .surname(u.getSurname())
                .username(u.getUsername())
                .roles(u.getRoles().stream().map(Role::getName).toList())
                .build();
        return res;
    }
}
