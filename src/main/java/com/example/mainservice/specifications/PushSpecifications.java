package com.example.mainservice.specifications;

import com.example.mainservice.models.entities.Push;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class PushSpecifications {

    public static Specification<Push> byCreatorUserId(UUID userId) {
        return (root, query, builder) -> {
            if (userId != null) {
                return builder.equal(root.get("creatorUser").get("id"), userId);
            }
            return null;
        };
    }

    public static Specification<Push> byFromDepartmentId(UUID departmentId) {
        return (root, query, builder) -> {
            if (departmentId != null) {
                return builder.equal(root.get("fromDepartment").get("id"), departmentId);
            }
            return null;
        };
    }

    public static Specification<Push> byToUserId(UUID userId) {
        return (root, query, builder) -> {
            if (userId != null) {
                query.distinct(true); // Ensure distinct results when joining collections
                return builder.equal(root.join("pushHistories").get("toUser").get("id"), userId);
            }
            return null;
        };
    }
}
