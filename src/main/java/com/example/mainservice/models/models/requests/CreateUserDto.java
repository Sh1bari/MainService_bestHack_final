package com.example.mainservice.models.models.requests;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {
    @NotNull(message = "userId cant be blank")
    private UUID userId;
    private String username;
    private String name;
    private String middleName;
    private String surname;
}
