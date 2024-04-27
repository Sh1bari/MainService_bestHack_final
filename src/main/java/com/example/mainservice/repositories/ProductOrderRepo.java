package com.example.mainservice.repositories;

import com.example.mainservice.models.entities.ProductOrder;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepo extends JpaRepository<ProductOrder, Long> {
}
