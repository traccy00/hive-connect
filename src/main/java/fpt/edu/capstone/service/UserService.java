package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.register.RegisterRequest;
import fpt.edu.capstone.entity.sprint1.Users;

import java.util.Optional;

public interface UserService {
    Users getUserById(long id);

    Optional<Users> findUserByUserName(String userName);

    Users saveUser(Users user);

    void registerUser(RegisterRequest request);

    Users getByUserName(String username);

    Users findById(long id);
}
