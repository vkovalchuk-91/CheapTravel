package org.geekhub.kovalchuk.repository.jpa;

import org.geekhub.kovalchuk.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
