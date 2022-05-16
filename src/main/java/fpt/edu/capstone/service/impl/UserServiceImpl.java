package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.common.JWTConstants;
import fpt.edu.capstone.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.login.LoginRequest;
import fpt.edu.capstone.entity.sprint1.Role;
import fpt.edu.capstone.entity.sprint1.User;
import fpt.edu.capstone.exception.ResourceNotFoundException;
import fpt.edu.capstone.repository.UserRepository;
import fpt.edu.capstone.service.LoginService;
import fpt.edu.capstone.service.RoleService;
import fpt.edu.capstone.service.UserService;
import fpt.edu.capstone.utils.enums.Enums;
import fpt.edu.capstone.utils.enums.SystemEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    LoginService loginService;

    @Override
    public User getUserById(long id) {
        return userRepository.getById(id);
    }

    @Override
    public Optional<User> findUserByUserName(String userName) {
        return userRepository.findByUsernameAndIsDeleted(userName,0);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public static boolean isHasRole(Set<Role> roles, String roleName) {
        for (Role role : roles)
            if (role.getName().equals(roleName))
                return true;
        return false;
    }
}
