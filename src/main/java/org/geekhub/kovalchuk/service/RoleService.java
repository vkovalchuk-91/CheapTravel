package org.geekhub.kovalchuk.service;

import org.geekhub.kovalchuk.model.entity.Role;
import org.geekhub.kovalchuk.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByRole(String role) {
        return roleRepository.findByRole(role);
    }

    public void save(Role role) {
        roleRepository.save(role);
    }
}
