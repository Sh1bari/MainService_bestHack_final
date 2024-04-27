package com.example.mainservice.repositories;

import com.example.mainservice.models.entities.Order;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findAll(Specification<Order> spec);
}
