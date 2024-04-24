package com.example.mainservice.models.models.responses;

import com.example.mainservice.models.entities.Department;
import com.example.mainservice.models.entities.DepartmentPermission;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentDtoRes {
    private UUID id;
    private String name;
    private Integer amountOfPeople;
    private List<DepartmentDtoRes> canSendTo;

    public static DepartmentDtoRes mapFromEntity(Department d){
        List<DepartmentDtoRes> list = d.getCanSendTo().stream()
                .map(DepartmentPermission::getDependentDepartment)
                .map(o->{
                    DepartmentDtoRes dto = DepartmentDtoRes.mapFromEntityWithoutCanSendTo(o);
                    dto.canSendTo = null;
                    return dto;
                })
                .toList();
        DepartmentDtoRes res = DepartmentDtoRes.builder()
                .id(d.getId())
                .name(d.getName())
                .amountOfPeople(d.getUserDepartmentRoles().size())
                .canSendTo(list.size()>0?list:null)
                .build();
        return res;
    }
    public static DepartmentDtoRes mapFromEntityWithoutCanSendTo(Department d){
        DepartmentDtoRes res = DepartmentDtoRes.builder()
                .id(d.getId())
                .name(d.getName())
                .amountOfPeople(d.getUserDepartmentRoles().size())
                .build();
        return res;
    }
}
