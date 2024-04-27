package com.example.mainservice.services;

import com.example.mainservice.exceptions.RoleNotFoundExc;
import com.example.mainservice.models.entities.Role;
import com.example.mainservice.repositories.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Vladimir Krasnov
 */
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepo roleRepo;

    public Role getUserRole(){
        return roleRepo.findByName("ROLE_USER")
                .orElseThrow(RoleNotFoundExc::new);
    }
}
