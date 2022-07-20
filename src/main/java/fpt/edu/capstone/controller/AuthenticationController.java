package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.login.LoginRequest;
import fpt.edu.capstone.dto.login.UserInforResponse;
import fpt.edu.capstone.dto.register.ChangePasswordRequest;
import fpt.edu.capstone.dto.register.RegisterRequest;
import fpt.edu.capstone.entity.*;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.security.TokenUtils;
import fpt.edu.capstone.service.*;
import fpt.edu.capstone.service.impl.SecurityUserServiceImpl;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.LogUtils;
import fpt.edu.capstone.utils.ResponseData;
import fpt.edu.capstone.utils.ResponseDataUser;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final UserService userService;

    private final CandidateService candidateService;

    private final RecruiterService recruiterService;

    private final AdminService adminService;

    private final AuthenticationManager authenticationManager;

    private final SecurityUserServiceImpl securityUserService;

    private final PasswordEncoder passwordEncoder;

    private final TokenUtils jwtTokenUtil;

    private final ConfirmTokenService confirmTokenService;

    @PostMapping("/login")
    @Operation(summary = "Login user")
    public ResponseDataUser login(@RequestBody @Valid LoginRequest request) throws Exception {
        try {
            authenticate(request.getUsername(), request.getPassword());
            String username = request.getUsername();
            logger.info("login with username {}", username);
            if (StringUtils.containsWhitespace(username) || StringUtils.containsWhitespace(request.getPassword())) {
                return new ResponseDataUser(Enums.ResponseStatus.ERROR.getStatus(),
                        ResponseMessageConstants.USERNAME_OR_PASSWORD_MUST_NOT_CONTAIN_ANY_SPACE_CHARACTERS);
            }

            final UserDetails userDetails = securityUserService.loadUserByUsername(username);

            Optional<Users> optionalUser = userService.findUserByUserName(username);
            if (!optionalUser.isPresent()) {
                throw new HiveConnectException("Username: " + username + "not found");
            }
            Users user = optionalUser.get();
            String token = jwtTokenUtil.generateToken(userDetails);

            user.setLastLoginTime(LocalDateTime.now());
            userService.saveUser(user);

            UserInforResponse response = new UserInforResponse();
            response.setUser(user);

            if(user.getRoleId() == 3){
                Optional<Candidate> candidate = candidateService.findCandidateByUserId(user.getId());
                response.setCandidate(candidate.get());
            }
            if(user.getRoleId() == 2){
                Optional<Recruiter> recruiter = recruiterService.findRecruiterByUserId(user.getId());
                if(!recruiter.isPresent()) {
                    throw new HiveConnectException("Người dùng không tồn tại");
                }
                response.setRecruiter(recruiter.get());
            }
            if(user.getRoleId() == 1){
                Optional<Admin> admin = adminService.findAdminByUserId(user.getId());
                response.setAdmin(admin.get());
            }

            return new ResponseDataUser(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.LOGIN_SUCCESS, response, token);

        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseDataUser(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("Tài khoản bị khóa", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Sai tên đăng nhập hoặc mật khẩu", e);
        }
    }

    @PostMapping("/register")
    @Operation(summary = "register user")
    @Transactional(rollbackOn = HiveConnectException.class)
    public ResponseDataUser register(@RequestBody RegisterRequest request) throws Exception {
        try {
            String username = request.getUsername();
            String password = request.getPassword();
            String email = request.getEmail();

            if (StringUtils.containsWhitespace(username) || StringUtils.containsWhitespace(password)) {
                return new ResponseDataUser(Enums.ResponseStatus.ERROR.getStatus(),
                        ResponseMessageConstants.USERNAME_OR_PASSWORD_MUST_NOT_CONTAIN_ANY_SPACE_CHARACTERS);
            }
            userService.registerUser(request);
            Users user = userService.getByUserName(username);
            if(user.getRoleId() == 3){
                candidateService.insertCandidate(user.getId());
            }
            if(user.getRoleId() == 2){
                recruiterService.insertRecruiter(user.getId());
            }
            if(user.getRoleId() == 1){
                adminService.insertAdmin(user.getId());
            }
            final UserDetails userDetails = securityUserService.loadUserByUsername(username);

            //region: Handle verify email
            ConfirmToken confirmToken = new ConfirmToken(user.getId());
            confirmTokenService.saveConfirmToken(confirmToken); // Generate token and save to DB
            ConfirmToken cf = confirmTokenService.getByUserId(user.getId()); // Cần lấy ra token để truyền vào url cho verify
            String mailToken = cf.getConfirmationToken();
            confirmTokenService.verifyEmailUser(email, mailToken);
            //endregion
            String jwtToken = jwtTokenUtil.generateToken(userDetails);
            return new ResponseDataUser(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.REGISTER_SUCCESS, user, jwtToken);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseDataUser(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @PostMapping("/resend-email/{username}")
    @Operation(summary = "resend email ")
    public ResponseData resendEmail(@PathVariable("username") String username) {
        try {
            Users user = userService.getByUserName(username);
            ConfirmToken cf = confirmTokenService.getByUserId(user.getId());
            String mailToken = cf.getConfirmationToken();
            confirmTokenService.verifyEmailUser(user.getEmail(), mailToken);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.RESEND_EMAIL_SUCCESS);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    /*
    Expect : line 115 sau khi gọi đến verify email,
    người dùng cần verify mail xong mới trả lại cho người dùng thông báo đăng kí thành công và jwt token để đăng nhập vào hệ thống
     */
    @PostMapping("/confirm-account")
    @Operation(summary = "confirm account")
    public ResponseDataUser confirmAccount(@RequestParam("token") String token) {
        try {
            // Cần lấy ra token để truyền vào url cho verify
            ConfirmToken cf = confirmTokenService.getByConfirmToken(token);
            if (cf == null) {
                throw new HiveConnectException("Token invalid");
            }
            //So sánh time expire và time trong db nếu quá hạn thì ko cho sử dụng token
//            if (LocalDateTime.now().isAfter(cf.getExpiredTime())) {
//                throw new HiveConnectException("Token has been expired");
//            }
            String mailToken = cf.getConfirmationToken();
            Users user = userService.getUserById(cf.getUserId());
            if (StringUtils.equals(token, mailToken)) {
                user.setVerifiedEmail(true);
                user.setActive(true);
            }
            userService.saveUser(user);
            final UserDetails userDetails = securityUserService.loadUserByUsername(user.getUsername());
            String tokenJwt = jwtTokenUtil.generateToken(userDetails);
            return new ResponseDataUser(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.REGISTER_SUCCESS, user, tokenJwt);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseDataUser(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @PostMapping("/password/{username}")
    @Operation(summary = "change password user")
    public ResponseData changePassword(@PathVariable(name = "username") String username, @RequestBody ChangePasswordRequest request) throws Exception {
        try {
            Optional<Users> optionalUsers = userService.findUserByUserName(username);
            if (!optionalUsers.isPresent()) {
                throw new HiveConnectException("User: " + username + "not found");
            }
            String oldPassword = request.getOldPassword();
            String newPassword = request.getNewPassword();
            String confirmPassword = request.getConfirmPassword();

            Users user = optionalUsers.get();
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                throw new HiveConnectException("Old password does not matches");
            }
            if (!StringUtils.equals(newPassword, confirmPassword)) {
                throw new HiveConnectException("Confirm password does not matches");
            }

            user.setPassword(passwordEncoder.encode(newPassword));
            userService.saveUser(user);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.CHANGE_PASSWORD_SUCCESS);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }
}
