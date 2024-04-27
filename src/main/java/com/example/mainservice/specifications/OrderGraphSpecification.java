package com.example.mainservice.specifications;

import com.example.mainservice.models.entities.Order;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class OrderGraphSpecification {

    public static Specification<Order> orderTimeBetween(LocalDateTime timeBefore, LocalDateTime timeAfter) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (timeBefore != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("orderTime"), timeBefore));
            }
            if (timeAfter != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("orderTime"), timeAfter));
            }
            return predicate;
        };
    }

}
