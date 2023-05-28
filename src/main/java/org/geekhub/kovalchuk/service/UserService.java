package org.geekhub.kovalchuk.service;

import org.apache.logging.log4j.util.Strings;
import org.geekhub.kovalchuk.model.dto.UserAccessSelectorDto;
import org.geekhub.kovalchuk.model.entity.Role;
import org.geekhub.kovalchuk.model.entity.User;
import org.geekhub.kovalchuk.repository.jpa.RoleRepository;
import org.geekhub.kovalchuk.repository.jpa.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean isUserFoundByUsernameAndPassword(String username, String password) {
        if (username.equals(Strings.EMPTY) || password.equals(Strings.EMPTY)) {
            return false;
        }
        User userWithIdentifiedUsername = userRepository.findByUsername(username);
        if (userWithIdentifiedUsername == null) {
            return false;
        }
        String passwordEncoded = userWithIdentifiedUsername.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, passwordEncoded);
    }

    public boolean isUserBlockedByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return !user.isActive();
    }

    public void addNewUser(User user) {
        user.setActive(true);
        Role roleUser = roleRepository.findByRole(ROLE_USER);
        user.setRoles(Set.of(roleUser));
        userRepository.save(user);
    }

    public List<UserAccessSelectorDto> getUsersForView() {
        List<UserAccessSelectorDto> users = new ArrayList<>();
        Role roleAdmin = roleRepository.findByRole(ROLE_ADMIN);
        userRepository.findAll().forEach(user -> users.add(new UserAccessSelectorDto(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles().contains(roleAdmin),
                !user.isActive())));
        return users;
    }

    public void updateUsers(Map<String, Boolean> updatedUsers) {
        Map<Long, Boolean> isAdmin = new HashMap<>();
        Map<Long, Boolean> isBlocked = new HashMap<>();
        for (Map.Entry<String, Boolean> userOption : updatedUsers.entrySet()) {
            String userOptionKey = userOption.getKey();
            if (userOptionKey.startsWith("admin_")) {
                Long userId = Long.parseLong(userOptionKey.replace("admin_", ""));
                isAdmin.put(userId, userOption.getValue());
            } else {
                Long userId = Long.parseLong(userOptionKey.replace("blocked_", ""));
                isBlocked.put(userId, userOption.getValue());
            }

        }

        List<User> currentUsers = userRepository.findAll();
        Role roleAdmin = roleRepository.findByRole(ROLE_ADMIN);
        for (User user : currentUsers) {
            if (isAdmin.get(user.getId()) && !user.getRoles().contains(roleAdmin)) {
                user.getRoles().add(roleAdmin);
                userRepository.save(user);
            }
            if (!isAdmin.get(user.getId()) && user.getRoles().contains(roleAdmin)) {
                user.getRoles().remove(roleAdmin);
                userRepository.save(user);
            }

            if (isBlocked.get(user.getId()) && user.isActive()) {
                user.setActive(false);
                userRepository.save(user);
            }
            if (!isBlocked.get(user.getId()) && !user.isActive()) {
                user.setActive(true);
                userRepository.save(user);
            }
        }
    }
}
