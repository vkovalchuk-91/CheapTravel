package org.geekhub.kovalchuk.service;

import org.geekhub.kovalchuk.model.entity.Role;
import org.geekhub.kovalchuk.repository.jpa.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByRole(String role) {
        return roleRepository.findByRole(role);
    }

    public void save(Role role) {
        roleRepository.save(role);
    }

    public boolean isRolesTableEmpty() {
        return roleRepository.count() == 0;
    }
    public void addRolesToDb() {
        Role userRole = new Role();
        userRole.setRole("ROLE_USER");
        roleRepository.save(userRole);
        Role adminRole = new Role();
        adminRole.setRole("ROLE_ADMIN");
        roleRepository.save(adminRole);
    }
}
