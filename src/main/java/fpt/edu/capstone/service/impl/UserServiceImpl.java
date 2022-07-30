package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.login.LoginGoogleRequest;
import fpt.edu.capstone.dto.register.ChangePasswordRequest;
import fpt.edu.capstone.dto.register.CountRegisterUserResponse;
import fpt.edu.capstone.dto.register.RegisterRequest;
import fpt.edu.capstone.dto.register.ResetPasswordRequest;
import fpt.edu.capstone.entity.Role;
import fpt.edu.capstone.entity.Users;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.UserRepository;
import fpt.edu.capstone.service.EmailService;
import fpt.edu.capstone.service.LoginService;
import fpt.edu.capstone.service.RoleService;
import fpt.edu.capstone.service.UserService;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.StringUtils;
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

    private final EmailService emailService;

    @Override
    public Users getUserById(long id) {
        Optional<Users> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new HiveConnectException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
        }
        return optionalUser.get();
    }

    @Override
    public Optional<Users> findUserByUserName(String userName) {
        return userRepository.findByUsernameAndIsDeleted(userName, 0);
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
        if (userExistByUserName.isPresent()) {
            throw new HiveConnectException("Tên đăng nhập đã được sử dụng");
        }
        Optional<Users> userExistByEmail = userRepository.findByEmail(request.getEmail());
        if (userExistByEmail.isPresent()) {
            throw new HiveConnectException("Email đã được sử dụng");
        }
        if (!StringUtils.equals(request.getPassword(), request.getConfirmPassword())) {
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
        if (user == null) {
            throw new HiveConnectException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
        }
        if (user.isLocked()) {
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
        if (user == null) {
            throw new HiveConnectException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
        }
        if (user.isActive()) {
            user.setActive(false);
        } else {
            user.setActive(true);
        }
        user.update();
        userRepository.save(user);
        return user;
    }

    @Override
    public void updatePhoneNumber(String phoneNumber, long userId) {
        userRepository.updatePhoneNumber(phoneNumber, userId);
    }

    @Override
    public Users loginGoogle(LoginGoogleRequest request) {
        if (StringUtils.isBlank(request.getEmail())) {
            throw new HiveConnectException(ResponseMessageConstants.DATA_INVALID);
        }
        return null;
    }

    @Override
    public void changePassword(String username, ChangePasswordRequest request) {
        Optional<Users> optionalUsers = findUserByUserName(username);
        if (!optionalUsers.isPresent()) {
            throw new HiveConnectException("Tên người dùng: " + username + "không tìm thấy.");
        }
        String oldPassword = request.getOldPassword().trim();
        String newPassword = request.getNewPassword().trim();
        String confirmPassword = request.getConfirmPassword().trim();

        Users user = optionalUsers.get();
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new HiveConnectException("Mật khẩu cũ không đúng.");
        }
        if (!StringUtils.equals(newPassword, confirmPassword)) {
            throw new HiveConnectException("Xác nhận mật khẩu không đúng.");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        saveUser(user);
    }


    @Override
    public Users findByEmail(String email) {
        if (!userRepository.findByEmail(email).isPresent()) {
            throw new HiveConnectException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
        }
        return userRepository.findByEmail(email).get();
    }

    @Override
    public Users findByResetPasswordToken(String resetPasswordToken) {
        if (!userRepository.findByResetPasswordToken(resetPasswordToken).isPresent()) {
            throw new HiveConnectException(ResponseMessageConstants.REQUEST_NOT_EXIST);
        }
        return userRepository.findByResetPasswordToken(resetPasswordToken).get();
    }

    @Override
    public void forgotPassword(String email) throws Exception {
        try {
            //verify user by email
            Users user = findByEmail(email);
            //generate reset token
            String token = RandomString.make(30);
            user.setResetPasswordToken(token);
            userRepository.save(user);
            //send mail with link reset token to user's email
            String resetPasswordLink = "http://localhost:8081/auth/reset_password?token=" + token;
            emailService.sendResetPasswordEmail(user.getEmail(), resetPasswordLink);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        if (request.getResetPasswordToken() == null || request.getResetPasswordToken().trim().isEmpty()) {
            throw new HiveConnectException(ResponseMessageConstants.TOKEN_INVALID);
        }
        //dựa vào đường link reset password từ email của user, xem có user nào trong db có token như vậy không
        Users user = findByResetPasswordToken(request.getResetPasswordToken());
        if ((request.getNewPassword() == null || request.getNewPassword().trim().isEmpty())
                || (request.getConfirmPassword() == null || request.getConfirmPassword().trim().isEmpty())) {
            throw new HiveConnectException(ResponseMessageConstants.REQUIRE_INPUT_MANDATORY_FIELD);
        }
        updatePassword(user, request);
    }

    @Override
    public void updatePassword(Users user, ResetPasswordRequest request) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(newPassword);
//        user.setPassword(encodedPassword);
//        user.setResetPasswordToken(null);
//        userRepository.save(user);

        String newPassword = request.getNewPassword().trim();
        String confirmPassword = request.getConfirmPassword().trim();
        if (!StringUtils.equals(newPassword, confirmPassword)) {
            throw new HiveConnectException("Xác nhận mật khẩu không đúng.");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        user.update();
        saveUser(user);
    }
}
