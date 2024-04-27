package com.example.mainservice.services;

import com.example.mainservice.exceptions.RegionNotFoundExc;
import com.example.mainservice.models.entities.Region;
import com.example.mainservice.repositories.RegionRepo;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionService {
    private final RegionRepo regionRepo;

    public Region findByName(String name){
        return regionRepo.findByName(name)
                .orElseThrow(RegionNotFoundExc::new);
    }
}
