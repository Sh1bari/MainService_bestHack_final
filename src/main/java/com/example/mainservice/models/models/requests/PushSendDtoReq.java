package com.example.mainservice.models.models.requests;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushSendDtoReq {
    private String title;
    private String body;
    private List<SendToDepartmentRolesDtoReq> toDepartmentRoles;
    private List<SendToUserIdDtoReq> toUserId;
}
