package com.example.mainservice.repositories;

import com.example.mainservice.models.entities.Order;
import com.example.mainservice.models.models.responses.GraphTableDataElementDto;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findAll(Specification<Order> spec);

    /*@Query("SELECT new com.example.mainservice.models.models.responses.GraphTableDataElementDto(pr, SUM(po.amount), SUM(po.totalPrice)) " +
            "FROM Product pr " +
            "LEFT JOIN pr.productOrders po " +
            "LEFT JOIN po.order o " +
            "WHERE o IN (SELECT o FROM Order o WHERE :spec) " +
            "GROUP BY pr")
    List<GraphTableDataElementDto> getGraphTableDataElements(@Param("spec") Specification<Order> spec);*/
}
