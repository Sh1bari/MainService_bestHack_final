package com.example.mainservice.services;

import com.example.mainservice.exceptions.RoleNotFoundExc;
import com.example.mainservice.models.entities.Role;
import com.example.mainservice.repositories.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Role getAdminRole(){
        return roleRepo.findByName("ROLE_ADMIN")
                .orElseThrow(RoleNotFoundExc::new);
    }

    public boolean existByRoleName(String role){
        return roleRepo.existsByName(role);
    }

    public void saveAll(List<Role> roles){
        roleRepo.saveAll(roles);
    }
}
