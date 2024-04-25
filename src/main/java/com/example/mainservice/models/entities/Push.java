package com.example.mainservice.models.entities;

import com.example.mainservice.models.models.requests.SendToDepartmentRolesDto;
import com.example.mainservice.models.models.requests.SendToUserIdDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

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
public class Push {
    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;
    private String title;
    private String body;
    @OneToMany(mappedBy = "push", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<PushHistory> pushHistories = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User creatorUser;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "department_id")
    private Department fromDepartment;

    @Basic
    private LocalDateTime pushTime = LocalDateTime.now();
}
