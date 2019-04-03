package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.entities.Role;
import com.adam.kiss.jbugger.exceptions.RoleNotFoundException;
import com.adam.kiss.jbugger.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findByRoleName(String roleName) throws RoleNotFoundException {
        return roleRepository.findByRoleName(roleName).orElseThrow(RoleNotFoundException::new);
    }
}
