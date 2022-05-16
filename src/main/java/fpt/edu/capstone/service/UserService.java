package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.login.LoginRequest;
import fpt.edu.capstone.entity.sprint1.User;

import java.util.Optional;

public interface UserService {
    User getUserById(long id);

    Optional<User> findUserByUserName(String userName);

    User saveUser(User user);
}
