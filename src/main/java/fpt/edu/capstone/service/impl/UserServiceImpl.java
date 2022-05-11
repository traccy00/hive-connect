package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.common.JWTConstants;
import fpt.edu.capstone.common.ResponseMessageConstants;
import fpt.edu.capstone.config.UserConfig;
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

    @Autowired
    UserConfig userConfig;

    @Override
    public User login(LoginRequest request) throws Exception {
        request.getEmail().toLowerCase().trim();
        User user = userRepository.findByEmail(request.getEmail());
        if(user == null){
            throw new ResourceNotFoundException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
        }
        //Check role  if Role user access denied
        Role role = roleService.getRoleById(user.getRoleId());
        if (
                isHasRole(new HashSet<>(Arrays.asList(role)), SystemEnum.RoleType.CANDIDATE.value()) ||
                isHasRole(new HashSet<>(Arrays.asList(role)), SystemEnum.RoleType.RECRUITER.value()) ||
                isHasRole(new HashSet<>(Arrays.asList(role)), SystemEnum.RoleType.ADMIN.value())) {
            throw new ResourceNotFoundException(ResponseMessageConstants.LOGIN_ACCESS_DENIED);
        }

        //validate status
        if (user.getIsDeleted() == Enums.UserStatus.Deleted.status()) {
            throw new ResourceNotFoundException(ResponseMessageConstants.USER_IS_DELETED);
        } else if (user.getIsDeleted() == Enums.UserStatus.Inactive.status()) {
            throw new ResourceNotFoundException(ResponseMessageConstants.USER_IS_INACTIVE);
        } else if (user.getIsDeleted() == Enums.UserStatus.Activated.status()) {
            boolean checkActiveMode = userConfig.getActiveMode().equalsIgnoreCase(JWTConstants.ACTIVE_MODE);
            if (checkActiveMode) {
                boolean authResult = loginService.login(request.getEmail(), request.getPassword());
                if (!authResult) {
                    throw new ResourceNotFoundException(ResponseMessageConstants.LOGIN_FAILED);
                }
            }
        }

        return user;
    }

    @Override
    public User getUserById(long id) {
        return userRepository.getById(id);
    }

    public static boolean isHasRole(Set<Role> roles, String roleName) {
        for (Role role : roles)
            if (role.getName().equals(roleName))
                return true;
        return false;
    }
}
