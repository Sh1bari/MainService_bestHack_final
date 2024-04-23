package com.example.mainservice.models.models.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDepartmentDtoReq {
    @NotBlank(message = "name cant be blank")
    @Min(value = 3, message = "name cant be less than 3")
    private String name;
}
