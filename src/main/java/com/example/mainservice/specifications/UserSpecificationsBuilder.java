package com.example.mainservice.specifications;

import com.example.mainservice.models.entities.User;
import com.example.mainservice.models.enums.UserRoleInDepartment;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserSpecificationsBuilder {

    private final List<Specification<User>> specifications;

    public UserSpecificationsBuilder() {
        this.specifications = new ArrayList<>();
    }

    public UserSpecificationsBuilder withNameContains(String keyword) {
        specifications.add(UserSpecifications.nameContainsIgnoreCase(keyword));
        return this;
    }
    public UserSpecificationsBuilder withDepartmentIdEquals(UUID departmentId) {
        specifications.add(UserSpecifications.withDepartmentIdEquals(departmentId));
        return this;
    }

    public UserSpecificationsBuilder withMiddleNameContains(String keyword) {
        specifications.add(UserSpecifications.middleNameContainsIgnoreCase(keyword));
        return this;
    }

    public UserSpecificationsBuilder withSurnameContains(String keyword) {
        specifications.add(UserSpecifications.surnameContainsIgnoreCase(keyword));
        return this;
    }

    public UserSpecificationsBuilder withUsernameContains(String keyword) {
        specifications.add(UserSpecifications.usernameContainsIgnoreCase(keyword));
        return this;
    }

    public UserSpecificationsBuilder hasDepartment(Boolean hasDepartment) {
        specifications.add(UserSpecifications.hasDepartment(hasDepartment));
        return this;
    }

    public UserSpecificationsBuilder hasRole(UserRoleInDepartment role) {
        specifications.add(UserSpecifications.hasRole(role));
        return this;
    }

    public Specification<User> build() {
        return specifications.stream().reduce(Specification::and).orElse(null);
    }
}
