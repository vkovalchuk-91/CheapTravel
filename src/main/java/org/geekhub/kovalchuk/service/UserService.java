package org.geekhub.kovalchuk.service;

import org.geekhub.kovalchuk.model.entity.User;
import org.geekhub.kovalchuk.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean isUserFoundByUsernameAndPassword(String username, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncoded = userRepository.findByUsername(username).getPassword();
        return passwordEncoder.matches(password, passwordEncoded);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
