package com.example.mainservice.models.models.requests;

import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDtoReq {
    private String name;
    private String middleName;
    private String surname;
}
