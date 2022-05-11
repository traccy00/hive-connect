//package fpt.edu.capstone.controller;
//
//import fpt.edu.capstone.common.ResponseMessageConstants;
//import fpt.edu.capstone.config.jwt.JwtTokenProvider;
//import fpt.edu.capstone.dto.admin.jwt.JwtAuthResponse;
//import fpt.edu.capstone.dto.login.LoginRequest;
//import fpt.edu.capstone.entity.sprint1.User;
//import fpt.edu.capstone.service.UserService;
//import fpt.edu.capstone.utils.Enums;
//import fpt.edu.capstone.utils.LogUtils;
//import fpt.edu.capstone.utils.ResponseData;
//import io.swagger.v3.oas.annotations.Operation;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//
//@RestController
//@RequestMapping("/api/v1/login")
//public class LoginController {
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//
//    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
//
//    @PostMapping("")
//    @Operation(summary = "Login user")
//    public ResponseData login(@RequestBody @Valid LoginRequest request, BindingResult result){
//        try {
//            logger.info("login with email {}", request.getEmail());
//            if (result.hasErrors()) {
//                return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.DATA_INVALID);
//            }
//            if (StringUtils.containsWhitespace(request.getEmail()) || StringUtils.containsWhitespace(request.getPassword())) {
//                return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(),
//                        ResponseMessageConstants.USERNAME_OR_PASSWORD_MUST_NOT_CONTAIN_ANY_SPACE_CHARACTERS);
//            }
//            User userAuth = userService.login(request);
//            String jwt = jwtTokenProvider.generateToken(userAuth);
//            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.LOGIN_SUCCESS, new JwtAuthResponse(jwt));
//        } catch (Exception e){
//            String msg = LogUtils.printLogStackTrace(e);
//            logger.error(msg);
//            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
//        }
//    }
//
//    @PostMapping("/register")
//    @Operation(summary = "register user")
//    public ResponseData register(){
//        return null;
//    }
//}
