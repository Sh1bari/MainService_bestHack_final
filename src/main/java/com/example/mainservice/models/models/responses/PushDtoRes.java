package com.example.mainservice.models.models.responses;

import com.example.mainservice.models.entities.Push;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushDtoRes {
    private UUID id;
    private String title;
    private String body;

    public static PushDtoRes mapFromEntity(Push p){
        PushDtoRes res = PushDtoRes.builder()
                .id(p.getId())
                .title(p.getTitle())
                .body(p.getBody())
                .build();
        return res;
    }
}
