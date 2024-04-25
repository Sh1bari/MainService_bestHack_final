package com.example.mainservice.specifications;

import com.example.mainservice.models.entities.Department;
import com.example.mainservice.models.entities.Push;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PushSpecificationsBuilder {
    private final List<Specification<Push>> specifications;

    public PushSpecificationsBuilder() {
        this.specifications = new ArrayList<>();
    }

    public PushSpecificationsBuilder byCreatorUserId(UUID userId) {
        specifications.add(PushSpecifications.byCreatorUserId(userId));
        return this;
    }
    public PushSpecificationsBuilder byFromDepartmentId(UUID userId) {
        specifications.add(PushSpecifications.byFromDepartmentId(userId));
        return this;
    }
    public PushSpecificationsBuilder byToUserId(UUID userId) {
        specifications.add(PushSpecifications.byToUserId(userId));
        return this;
    }

    public Specification<Push> build() {
        return specifications.stream().reduce(Specification::and).orElse(null);
    }
}
