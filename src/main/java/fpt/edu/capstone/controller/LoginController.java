package fpt.edu.capstone.controller;

import fpt.edu.capstone.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.login.LoginRequest;
import fpt.edu.capstone.entity.sprint1.User;
import fpt.edu.capstone.exception.ResourceNotFoundException;
import fpt.edu.capstone.security.TokenUtils;
import fpt.edu.capstone.service.UserService;
import fpt.edu.capstone.service.impl.SecurityUserServiceImpl;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.LogUtils;
import fpt.edu.capstone.utils.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/login")
@AllArgsConstructor
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final SecurityUserServiceImpl securityUserService;

    private final TokenUtils jwtTokenUtil;

    @PostMapping("")
    @Operation(summary = "Login user")
    public ResponseData login(@RequestBody @Valid LoginRequest request) throws Exception{
        try {
            authenticate(request.getUsername(), request.getPassword());
            String username = request.getUsername();
            logger.info("login with username {}", username);
            if (StringUtils.containsWhitespace(username) || StringUtils.containsWhitespace(request.getPassword())) {
                return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(),
                        ResponseMessageConstants.USERNAME_OR_PASSWORD_MUST_NOT_CONTAIN_ANY_SPACE_CHARACTERS);
            }

            final UserDetails userDetails = securityUserService.loadUserByUsername(username);

            Optional<User> optionalUser = userService.findUserByUserName(username);
            if(!optionalUser.isPresent()){
                throw new ResourceNotFoundException("Username: "+username+ "not found");
            }
            User user = optionalUser.get();
            String token = jwtTokenUtil.generateToken(userDetails);

            user.setLastLoginTime(LocalDateTime.now());
            userService.saveUser(user);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.LOGIN_SUCCESS,token);

        } catch (Exception e){
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/register")
    @Operation(summary = "register user")
    public ResponseData register(){
        return null;
    }
}
