package com.example.mainservice.models.models.requests;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "phoneNumber cant be blank")
    private String phoneNumber;
    @NotBlank(message = "username cant be blank")
    private String username;
    private String name;
    private String middleName;
    private String surname;
}
