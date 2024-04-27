package com.example.mainservice.models.entities;

import com.example.mainservice.models.enums.UserStatus;
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
@Table(name = "users")
public class User {
    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    private UUID authId;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String phoneNumber;

    private String name;
    private String middleName;
    private String surname;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ACTIVE;

    @Basic
    private LocalDateTime registrationTime = LocalDateTime.now();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    public User(String username,
                String name,
                String middleName,
                String surname,
                String phoneNumber){
        this.username = username;
        this.name = name;
        this.middleName = middleName;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.status = UserStatus.ACTIVE;
        this.registrationTime = LocalDateTime.now();
        this.roles = new ArrayList<>();
        this.orders = new ArrayList<>();
    }
}
