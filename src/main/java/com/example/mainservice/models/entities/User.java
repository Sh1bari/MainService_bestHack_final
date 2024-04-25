package com.example.mainservice.models.entities;

import com.example.mainservice.models.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;
    @Column(nullable = false, updatable = false, unique = true)
    private UUID authId;

    //info
    private String name;
    private String middleName;
    private String surname;
    private String username;
    @Basic
    private LocalDateTime createDate = LocalDateTime.now();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<UserDepartmentRole> departmentRoles = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "user_push_tokens", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "push_token")
    private Set<String> pushTokens = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private UserRole globalRole = UserRole.ROLE_USER;

    /*@Column(unique = true)
    private String mail;*/

}
