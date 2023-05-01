package org.geekhub.kovalchuk.repository;

import org.geekhub.kovalchuk.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}
