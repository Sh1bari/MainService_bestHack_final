package com.example.mainservice.repositories;

import com.example.mainservice.models.entities.Order;
import com.example.mainservice.models.entities.User;
import com.example.mainservice.models.enums.OrderStatus;
import com.example.mainservice.models.models.responses.GraphTableDataElementDto;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findAll(Specification<Order> spec);

    Order findByUserAndOrderStatus(User user, OrderStatus orderStatus);
    List<Order> findAllByUserAndOrderStatus(User user, OrderStatus orderStatus);
}
