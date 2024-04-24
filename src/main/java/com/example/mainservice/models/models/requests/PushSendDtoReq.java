package com.example.mainservice.models.models.requests;

import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushSendDtoReq {
    private String title;
    private String body;
    private SendToDtoReq to;
}
