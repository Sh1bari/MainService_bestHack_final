package com.example.mainservice.models.entities;

import com.example.mainservice.models.enums.UserRoleInDepartment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDepartmentRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @Basic
    private LocalDateTime createTime = LocalDateTime.now();

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
    @JoinColumn(name = "department_id")
    private Department department;

    @ElementCollection(targetClass = UserRoleInDepartment.class)
    @Enumerated(EnumType.STRING)
    private List<UserRoleInDepartment> roles;
}
