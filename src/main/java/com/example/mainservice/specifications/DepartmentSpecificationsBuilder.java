package com.example.mainservice.specifications;

import com.example.mainservice.models.entities.Department;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DepartmentSpecificationsBuilder {

    private final List<Specification<Department>> specifications;

    public DepartmentSpecificationsBuilder() {
        this.specifications = new ArrayList<>();
    }

    public DepartmentSpecificationsBuilder withNameContains(String name) {
        specifications.add(DepartmentSpecifications.withNameContains(name));
        return this;
    }

    public Specification<Department> build() {
        return specifications.stream().reduce(Specification::and).orElse(null);
    }
}
