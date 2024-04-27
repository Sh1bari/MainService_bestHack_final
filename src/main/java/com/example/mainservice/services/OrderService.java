package com.example.mainservice.services;

import com.example.mainservice.repositories.OrderRepo;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    //private
}
