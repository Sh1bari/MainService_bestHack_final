package com.example.mainservice.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.scheduling.quartz.LocalDataSourceJobStore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(unique = true)
    private String name;

    @Basic
    private LocalDateTime createDate = LocalDateTime.now();


    @OneToMany(mappedBy = "mainDepartment", orphanRemoval = true)
    private List<DepartmentPermission> canSentTo = new ArrayList<>();

    @OneToMany(mappedBy = "dependentDepartment", orphanRemoval = true)
    private List<DepartmentPermission> canGetFrom = new ArrayList<>();

    @OneToMany(mappedBy = "department", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<UserDepartmentRole> userDepartmentRoles = new ArrayList<>();

}
