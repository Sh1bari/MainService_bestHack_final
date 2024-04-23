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
    private List<DepartmentDtoRes> canSentTo;

    public static DepartmentDtoRes mapFromEntity(Department d){
        DepartmentDtoRes res = DepartmentDtoRes.builder()
                .name(d.getName())
                .amountOfPeople(d.getUsers().size())
                .canSentTo(d.getCanSentTo().stream()
                        .map(DepartmentPermission::getDependentDepartment)
                        .map(o->{
                            DepartmentDtoRes dto = DepartmentDtoRes.mapFromEntity(o);
                            dto.canSentTo = null;
                            return dto;
                        })
                        .collect(Collectors.toList()))
                .build();
        return res;
    }
}
