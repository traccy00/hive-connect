package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.register.CountRegisterUserResponse;
import fpt.edu.capstone.dto.register.RegisterRequest;
import fpt.edu.capstone.entity.Role;
import fpt.edu.capstone.entity.Users;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.UserRepository;
import fpt.edu.capstone.service.LoginService;
import fpt.edu.capstone.service.RoleService;
import fpt.edu.capstone.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final RoleService roleService;

    private final LoginService loginService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Users getUserById(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public Optional<Users> findUserByUserName(String userName) {
        return userRepository.findByUsernameAndIsDeleted(userName,0);
    }

    @Override
    public Users saveUser(Users user) {
        user.update();
        return userRepository.save(user);
    }

    @Override
    public void registerUser(RegisterRequest request) {
        Optional<Role> optionalRole = roleService.findRoleById(request.getRoleId());
        if (!optionalRole.isPresent()) {
            throw new HiveConnectException("Loại vai trò người dùng không tồn tại trong hệ thống");
        }
        //check exist email username
//        Optional <Users> checkExisted = userRepository.checkExistedUserByUsernameOrEmail(request.getUsername(),request.getEmail());
//        if(checkExisted.isPresent()){
//            throw new HiveConnectException("Username or email is already existed!");
//        }

        Optional<Users> userExistByUserName = userRepository.findByUsername(request.getUsername());
        if(userExistByUserName.isPresent()) {
            throw new HiveConnectException("Tên đăng nhập đã được sử dụng");
        }
        Optional<Users> userExistByEmail = userRepository.findByEmail(request.getEmail());
        if(userExistByEmail.isPresent()) {
            throw new HiveConnectException("Email đã được sử dụng");
        }
        if(!StringUtils.equals(request.getPassword(),request.getConfirmPassword())){
            throw new HiveConnectException("Mật khẩu xác nhận không đúng");
        }
        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRoleId(request.getRoleId());
        user.setVerifiedEmail(false);
        user.setVerifiedPhone(false);
        user.create();
        userRepository.save(user);
    }

    @Override
    public Users getByUserName(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public Users findById(long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public Optional<Users> findByIdOp(long id) {
        return userRepository.findById(id);
    }

    @Override
    public void updateAvatarUrl(String avatarId, long id) {
        userRepository.updateAvatarUrl(avatarId, id);
    }

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<Users> findByPhoneNumber(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    public HashMap<String, List<CountRegisterUserResponse>> countUser() {
        List<CountRegisterUserResponse> registerToday = userRepository.countUserRegisterToday();
        List<CountRegisterUserResponse> registerMonthAgo = userRepository.countUserRegisterMonthAgo();
        List<CountRegisterUserResponse> totalRegisterUsers = userRepository.countAllUsersRegister();
        HashMap<String, List<CountRegisterUserResponse>> responseHashMap = new HashMap<>();
        responseHashMap.put("Total", totalRegisterUsers);
        responseHashMap.put("Month", registerMonthAgo);
        responseHashMap.put("Today", registerToday);
        return responseHashMap;
    }

    @Override
    public Users lockUnlockUser(long userId) {
        Users user = userRepository.getById(userId);
        if(user == null) {
            throw new HiveConnectException("Người dùng không tồn tại");
        }
        if(user.isLocked()) {
            user.setLocked(false);
        } else {
            user.setLocked(true);
        }
        user.update();
        userRepository.save(user);
        return user;
    }

    @Override
    public Users activeDeactiveUser(long userId) {
        Users user = userRepository.getById(userId);
        if(user == null) {
            throw new HiveConnectException("Người dùng không tồn tại");
        }
        if(user.isActive()) {
            user.setActive(false);
        } else {
            user.setActive(true);
        }
        user.update();
        userRepository.save(user);
        return user;
    }
}
