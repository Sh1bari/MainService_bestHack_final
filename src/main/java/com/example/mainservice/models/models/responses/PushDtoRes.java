package com.example.mainservice.models.models.responses;

import com.example.mainservice.models.entities.Push;
import com.example.mainservice.utils.Formatter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PushDtoRes {
    private UUID id;
    private String title;
    private String body;
    private UserDtoRes creator;
    private String time;
    private DepartmentDtoRes fromDepartment;
    private List<PushHistoryDtoRes> history;

    public static PushDtoRes mapFromEntity(Push p){
        DepartmentDtoRes dep = p.getFromDepartment()!=null? DepartmentDtoRes.mapFromEntityWithoutCanSendTo(p.getFromDepartment()): null;
        PushDtoRes res = PushDtoRes.builder()
                .id(p.getId())
                .title(p.getTitle())
                .body(p.getBody())
                .time(p.getPushTime().format(Formatter.formatter))
                .creator(UserDtoRes.mapFromEntityWithoutDepartmentRoles(p.getCreatorUser()))
                .fromDepartment(dep)
                .history(p.getPushHistories().stream().map(PushHistoryDtoRes::mapFromEntityWithoutPush).toList())
                .build();
        return res;
    }

    public static PushDtoRes mapFromEntityWithoutHistory(Push p){
        DepartmentDtoRes dep = p.getFromDepartment()!=null? DepartmentDtoRes.mapFromEntityWithoutCanSendTo(p.getFromDepartment()): null;
        PushDtoRes res = PushDtoRes.builder()
                .id(p.getId())
                .time(p.getPushTime().format(Formatter.formatter))
                .title(p.getTitle())
                .body(p.getBody())
                .creator(UserDtoRes.mapFromEntityWithoutDepartmentRoles(p.getCreatorUser()))
                .fromDepartment(dep)
                .build();
        return res;
    }
}
