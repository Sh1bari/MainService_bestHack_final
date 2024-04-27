package com.example.mainservice.services;

import com.example.mainservice.models.entities.Product;
import com.example.mainservice.repositories.ProductRepo;
import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;

    public List<Product> getAll(){
        return productRepo.findAll();
    }

    public List<Product> findAllBySearch(String pattern, Pageable pageable){
        return productRepo.findAllByNameContainingIgnoreCase(pattern, pageable).toList();
    }
}
