package com.example.mainservice.services;

import com.example.mainservice.models.entities.Product;
import com.example.mainservice.repositories.ProductRepo;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;

    public List<Product> getAll(){
        return productRepo.findAll();
    }
}
