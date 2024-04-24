package com.example.mainservice.specifications;

import com.example.mainservice.models.entities.Department;
import com.example.mainservice.models.entities.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;

public class DepartmentSpecifications {
    public static Specification<Department> withNameContains(String keyword) {
        return (Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (keyword == null || keyword.isBlank()) {
                return null;
            } else {
                String pattern = "%" + keyword.toLowerCase() + "%";
                return builder.like(builder.lower(root.get("name")), pattern);
            }
        };
    }
}
