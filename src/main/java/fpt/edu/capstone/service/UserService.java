package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.login.LoginGoogleRequest;
import fpt.edu.capstone.dto.register.ChangePasswordRequest;
import fpt.edu.capstone.dto.register.CountRegisterUserResponse;
import fpt.edu.capstone.dto.register.RegisterRequest;
import fpt.edu.capstone.dto.register.ResetPasswordRequest;
import fpt.edu.capstone.entity.Users;

import java.util.HashMap;
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

    Optional<Users> findByPhoneNumber(String phone);

    HashMap<String, List<CountRegisterUserResponse>> countUser();

    Users lockUnlockUser(long userId);

    Users activeDeactiveUser(long userId);

    void updatePhoneNumber(String phoneNumber, long userId);

    void changePassword(String username, ChangePasswordRequest request);

    Users findByEmail(String email);

    Users findByResetPasswordToken(String resetPasswordToken);

    void forgotPassword(String email) throws Exception;

    void updatePassword(Users user, ResetPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);

    void updateIsVerifyPhone(boolean isVerifyPhone, long userId);
}
