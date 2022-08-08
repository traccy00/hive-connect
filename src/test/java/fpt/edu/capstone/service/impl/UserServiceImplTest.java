package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.register.RegisterRequest;
import fpt.edu.capstone.dto.register.ResetPasswordRequest;
import fpt.edu.capstone.entity.Role;
import fpt.edu.capstone.entity.Users;
import fpt.edu.capstone.repository.RoleRepository;
import fpt.edu.capstone.repository.UserRepository;
import fpt.edu.capstone.service.EmailService;
import fpt.edu.capstone.service.RoleService;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.mail.MessagingException;
import javax.swing.text.html.Option;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
public class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleService roleService;

    @Mock
    RoleRepository roleRepository;

    @Mock
    EmailService emailService;

    @Test
    void findUserByUserNameTest() {
        Users users = new Users();
        users.setId(1L);
        users.setUsername("NamNH");
        Mockito.when(userRepository.findByUsernameAndIsDeleted(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())).thenReturn(Optional.of(users));
        Optional<Users> optional = userService.findUserByUserName("NamNH");
        assertEquals("NamNH", optional.get().getUsername());
    }

    @Test
    void getUserByIdTest() {
        Users users = new Users();
        users.setId(1L);
        Mockito.when(userRepository.getUserById(ArgumentMatchers.anyLong())).thenReturn(users);
        Users result = userService.getUserById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void saveUserTest() {
        Users users = new Users();
        users.setId(1L);
        Mockito.when(userRepository.save(ArgumentMatchers.any(Users.class))).thenReturn(users);
        Users result = userService.saveUser(users);
        assertEquals(1L, result.getId());
    }

    @Test
    void registerUserTest() {
        RegisterRequest request = new RegisterRequest();
        request.setRoleId(1L);
        request.setUsername("namnh");
        request.setEmail("namnh@gmail.com");
//        Role role = new Role();
//        Mockito.when(roleRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(role));
//        Optional<Role> optional = roleService.findRoleById(1L);

        Users users = new Users();
        users.setId(1L);
        users.setUsername("namnh");
        users.setRoleId(1L);
        userService.saveUser(users);

//        assertEquals(1L, optional.get().getId());
    }

    @Test
    void registerGoogleUserTest() {
        RegisterRequest request = new RegisterRequest();
        request.setRoleId(1L);
        request.setUsername("namnh");
        request.setEmail("namnh@gmail.com");
        Users users = new Users();
        users.setId(1L);
        users.setUsername("namnh");
        users.setRoleId(1L);
        userService.saveUser(users);
    }

    @Test
    void getByUserNameTest() {
        Users users = new Users();
        users.setUsername("namnh");
        Mockito.when(userRepository.getByUsername(ArgumentMatchers.anyString())).thenReturn(users);
        Users result = userService.getByUserName("namnh");
        assertEquals("namnh", result.getUsername());
    }

    @Test
    void findByIdTest() {
        Users users = new Users();
        users.setId(1L);
        Mockito.when(userRepository.getUserById(ArgumentMatchers.anyLong())).thenReturn(users);
        Users result = userService.findById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void findByIdOpTest(){
        Users users = new Users();
        users.setId(1L);
        Mockito.when(userRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(users));
        Optional<Users> result = userService.findByIdOp(1L);
        assertEquals(1L, result.get().getId());
    }

    @Test
    void findAllTest(){
        List<Users> users = new ArrayList<>();
        Mockito.when(userRepository.findAll()).thenReturn(users);
        List<Users> result = userService.findAll();
        assertEquals(0, result.size());
    }

    @Test
    void findByPhoneNumberTest(){
        Users users = new Users();
        users.setPhone("0967445450");
        Mockito.when(userRepository.findByPhone(ArgumentMatchers.anyString())).thenReturn(Optional.of(users));
        Optional<Users> result = userService.findByPhoneNumber("0967445450");
        assertEquals("0967445450", result.get().getPhone());
    }

    @Test
    void countUserTest(){
    //TODO: chưa xử lí được
    }

    @Test
    void lockUnlockUserTest(){
        Users users = new Users();
        users.setId(1L);
        users.setUsername("namnh");
        users.setRoleId(123L);
        Mockito.when(userRepository.getById(ArgumentMatchers.anyLong())).thenReturn(users);
        Mockito.when(userRepository.save(ArgumentMatchers.any(Users.class))).thenReturn(users);
        Users result = userService.lockUnlockUser(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void activeDeactiveUserTest(){
        Users users = new Users();
        users.setId(1L);
        users.setUsername("namnh");
        users.setRoleId(123L);
        Mockito.when(userRepository.getById(ArgumentMatchers.anyLong())).thenReturn(users);
        Mockito.when(userRepository.save(ArgumentMatchers.any(Users.class))).thenReturn(users);
        Users result = userService.activeDeactiveUser(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void updatePhoneNumberTest(){
        String phoneNumber ="0967445450";
        long userId = 1L;
        userService.updatePhoneNumber(phoneNumber, userId);
    }

    @Test
    void changePasswordTest(){
        Users users = new Users();
        users.setUsername("namnh");
//        Mockito.when(userService.findUserByUserName(ArgumentMatchers.anyString())).thenReturn(Optional.of(users));
        users.setPassword("123");
        userService.saveUser(users);
    }

    @Test
    void findByEmailTest(){
        Users users = new Users();
        users.setEmail("nam@gmail.com");
        Mockito.when(userRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.of(users));
        Users result = userService.findByEmail("nam@gmail.com");
        assertEquals("nam@gmail.com", result.getEmail());
    }

    @Test
    void findByResetPasswordTokenTest(){
        Users users = new Users();
        users.setResetPasswordToken("ghjk324adfua");
        Mockito.when(userRepository.findByResetPasswordToken(ArgumentMatchers.anyString())).thenReturn(Optional.of(users));
        Users result = userService.findByResetPasswordToken("ghjk324adfua");
        assertEquals("ghjk324adfua", result.getResetPasswordToken());
    }

    @Test
    void forgotPasswordTest() throws MessagingException, UnsupportedEncodingException {
        Users users = new Users();
        String token = RandomString.make(30);
        users.setEmail("nam@gmail.com");
        String resetPasswordLink = "http://localhost:4200/auth/forgot-password?token=" + token;
        emailService.sendResetPasswordEmail(users.getEmail(), resetPasswordLink);
    }

    @Test
    void resetPasswordTest(){
        //TODO
    }

    @Test
    void updateIsVerifyPhoneTest(){
        Users users = new Users();
        users.setId(1L);
        users.setVerifiedPhone(false);
        userRepository.updateIsVerifyPhone(users.isVerifiedPhone(), users.getId());
    }

    @Test
    void findByPhoneAndIdIsNotInTest(){
        Users users = new Users();
        users.setPhone("0967445450");
        users.setId(1L);
        Mockito.when(userRepository.findByPhoneAndIdIsNotIn(ArgumentMatchers.anyString(), ArgumentMatchers.anyLong())).thenReturn(users);
        Users user = userService.findByPhoneAndIdIsNotIn("0967445450", 1L);
        assertEquals("0967445450", user.getPhone());
    }

    @Test
    void updatePasswordTest(){
        Users users = new Users();
        users.setPassword("123456");
        users.setId(1L);
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setNewPassword("123456789");
        request.setConfirmPassword("123456789");
        userService.updatePassword(users, request);
    }
}
