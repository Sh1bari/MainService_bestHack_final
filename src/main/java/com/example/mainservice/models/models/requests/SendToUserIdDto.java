package com.example.mainservice.models.models.requests;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendToUserIdDto {
    private UUID userId;
}
