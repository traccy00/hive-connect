package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.register.RegisterRequest;
import fpt.edu.capstone.entity.Users;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Users getUserById(long id);

    Optional<Users> findUserByUserName(String userName);

    Users saveUser(Users user);

    void registerUser(RegisterRequest request);

    Users getByUserName(String username);

    Users findById(long id);

    Optional<Users> findByIdOp(long id);

    void updateAvatarUrl(String avatarId, long id);

    List<Users> findAll();
}
