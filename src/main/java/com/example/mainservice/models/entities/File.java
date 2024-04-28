package com.example.mainservice.models.entities;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String bucket;
    private String name;
    private String path;
}
