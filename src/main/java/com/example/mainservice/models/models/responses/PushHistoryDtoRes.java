package com.example.mainservice.models.models.responses;

import com.example.mainservice.models.entities.PushHistory;
import com.example.mainservice.utils.Formatter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PushHistoryDtoRes {
    private String pushTime;
    private UserDtoRes toUser;
    private PushDtoRes push;

    public static PushHistoryDtoRes mapFromEntity(PushHistory p){
        PushHistoryDtoRes res = PushHistoryDtoRes.builder()
                .pushTime(p.getPushTime().format(Formatter.formatter))
                .toUser(UserDtoRes.mapFromEntityWithoutDepartmentRoles(p.getToUser()))
                .push(PushDtoRes.mapFromEntityWithoutHistory(p.getPush()))
                .build();
        return res;
    }
    public static PushHistoryDtoRes mapFromEntityWithoutPush(PushHistory p){
        PushHistoryDtoRes res = PushHistoryDtoRes.builder()
                .pushTime(p.getPushTime().format(Formatter.formatter))
                .toUser(UserDtoRes.mapFromEntityWithoutDepartmentRoles(p.getToUser()))
                .build();
        return res;
    }
}
