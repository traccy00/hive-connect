package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.register.*;
import fpt.edu.capstone.entity.Role;
import fpt.edu.capstone.entity.Users;
import fpt.edu.capstone.entity.WorkExperience;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.UserRepository;
import fpt.edu.capstone.service.EmailService;
import fpt.edu.capstone.service.RoleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private RoleService mockRoleService;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private EmailService mockEmailService;

    private UserServiceImpl userServiceImplUnderTest;

    @Before
    public void setUp() {
        userServiceImplUnderTest = new UserServiceImpl(mockUserRepository, mockRoleService, mockPasswordEncoder,
                mockEmailService);
    }

    private Users users(){
        Users users = new Users();
        users.setId(0L);
        users.setUsername("username");
        users.setPassword("password");
        users.setEmail("email");
        users.setPhone("0967445450");
        users.setRoleId(0L);
        users.setIsDeleted(0);
        users.setLastLoginTime(LocalDateTime.now());
        users.setVerifiedEmail(false);
        users.setVerifiedPhone(false);
        users.setActive(true);
        users.setLocked(false);
        users.setAvatar("avatar");
        users.setResetPasswordToken("setResetPasswordToken");
        users.setGoogle(false);
        return users;
    }

    private RegisterRequest registerRequest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        registerRequest.setConfirmPassword("password");
        registerRequest.setEmail("email");
        registerRequest.setPhone("0967445450");
        registerRequest.setRoleId(0L);
        return registerRequest;
    }

    private Role role(){
        Role role = new Role();
        role.setId(0L);
        role.setName("name");
        role.setDescription("description");
        return role;
    }

    private ChangePasswordRequest changePasswordRequest(){
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword("password");
        changePasswordRequest.setNewPassword("123");
        changePasswordRequest.setConfirmPassword("123");
        return changePasswordRequest;
    }

    private ResetPasswordRequest resetPasswordRequest(){
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setNewPassword("123");
        resetPasswordRequest.setConfirmPassword("123");
        resetPasswordRequest.setResetPasswordToken("resetPasswordToken");
        return resetPasswordRequest;
    }

    @Test
    public void testGetUserById() {
        final Users users = users();
        when(mockUserRepository.getUserById(0L)).thenReturn(users);
        final Users result = userServiceImplUnderTest.getUserById(0L);
    }

    @Test
    public void testFindUserByUserName() {
        final Optional<Users> optional = Optional.of(users());
        when(mockUserRepository.findByUsernameAndIsDeleted("userName", 0)).thenReturn(optional);
        final Optional<Users> result = userServiceImplUnderTest.findUserByUserName("userName");
    }

    @Test
    public void testFindUserByUserName_UserRepositoryReturnsAbsent() {
        when(mockUserRepository.findByUsernameAndIsDeleted("userName", 0)).thenReturn(Optional.empty());
        final Optional<Users> result = userServiceImplUnderTest.findUserByUserName("userName");
        assertThat(result).isEmpty();
    }

    @Test
    public void testSaveUser() {
        final Users user = users();
        final Users users = users();
        when(mockUserRepository.save(any(Users.class))).thenReturn(users);
        final Users result = userServiceImplUnderTest.saveUser(user);
    }

    @Test
    public void testRegisterUser() {
        final RegisterRequest request = registerRequest();
        final Optional<Role> optional = Optional.of(role());
        when(mockRoleService.findRoleById(0L)).thenReturn(optional);
        final Optional<Users> optional1 = Optional.of(users());
        when(mockUserRepository.findByUsername("usernamee")).thenReturn(optional1);
        final Optional<Users> optional2 = Optional.of(users());
        when(mockUserRepository.findByEmail("emaill")).thenReturn(optional2);
        when(mockPasswordEncoder.encode("password")).thenReturn("password");
        final Users users = users();
        when(mockUserRepository.save(any(Users.class))).thenReturn(users);
        userServiceImplUnderTest.registerUser(request);
        verify(mockUserRepository).save(any(Users.class));
    }

    @Test
    public void testRegisterUser_RoleServiceReturnsAbsent() {
        final RegisterRequest request =registerRequest();
        when(mockRoleService.findRoleById(0L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userServiceImplUnderTest.registerUser(request))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testRegisterUser_UserRepositoryFindByUsernameReturnsAbsent() {
        final RegisterRequest request = registerRequest();
        final Optional<Role> optional = Optional.of(role());
        when(mockRoleService.findRoleById(0L)).thenReturn(optional);
        when(mockUserRepository.findByUsername("username")).thenReturn(Optional.empty());
        final Optional<Users> optional1 = Optional.of(users());
        when(mockUserRepository.findByEmail("eemail")).thenReturn(optional1);
        when(mockPasswordEncoder.encode("password")).thenReturn("password");
        final Users users = users();
        when(mockUserRepository.save(any(Users.class))).thenReturn(users);
        userServiceImplUnderTest.registerUser(request);
        verify(mockUserRepository).save(any(Users.class));
    }

    @Test
    public void testRegisterUser_UserRepositoryFindByEmailReturnsAbsent() {
        final RegisterRequest request = registerRequest();
        final Optional<Role> optional = Optional.of(role());
        when(mockRoleService.findRoleById(0L)).thenReturn(optional);
        final Optional<Users> optional1 = Optional.of(users());
        when(mockUserRepository.findByUsername("usernamea")).thenReturn(optional1);
        when(mockUserRepository.findByEmail("email")).thenReturn(Optional.empty());
        when(mockPasswordEncoder.encode("password")).thenReturn("password");
        final Users users = users();
        when(mockUserRepository.save(any(Users.class))).thenReturn(users);
        userServiceImplUnderTest.registerUser(request);
        verify(mockUserRepository).save(any(Users.class));
    }

    @Test
    public void testRegisterGoogleUser() {
        final RegisterGoogleRequest request = new RegisterGoogleRequest();
        request.setEmail("email");
        request.setRoleId(0L);
        final Optional<Role> optional = Optional.of(role());
        when(mockRoleService.findRoleById(0L)).thenReturn(optional);
        final Optional<Users> optional1 = Optional.of(users());
        when(mockUserRepository.findByUsername("username")).thenReturn(optional1);
        final Optional<Users> optional2 = Optional.of(users());
        when(mockUserRepository.findByEmail("emailll")).thenReturn(optional2);
        when(mockPasswordEncoder.encode("1")).thenReturn("password");
        final Users users = users();
        when(mockUserRepository.save(any(Users.class))).thenReturn(users);
        userServiceImplUnderTest.registerGoogleUser(request);
        verify(mockUserRepository).save(any(Users.class));
    }

    @Test
    public void testRegisterGoogleUser_RoleServiceReturnsAbsent() {
        final RegisterGoogleRequest request = new RegisterGoogleRequest();
        request.setEmail("email");
        request.setRoleId(0L);
        when(mockRoleService.findRoleById(0L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userServiceImplUnderTest.registerGoogleUser(request))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testRegisterGoogleUser_UserRepositoryFindByUsernameReturnsAbsent() {
        final RegisterGoogleRequest request = new RegisterGoogleRequest();
        request.setEmail("email");
        request.setRoleId(0L);
        final Optional<Role> optional = Optional.of(role());
        when(mockRoleService.findRoleById(0L)).thenReturn(optional);

        when(mockUserRepository.findByUsername("username")).thenReturn(Optional.empty());
        final Optional<Users> optional1 = Optional.of(users());
        when(mockUserRepository.findByEmail("asemail")).thenReturn(optional1);
        when(mockPasswordEncoder.encode("1")).thenReturn("password");
        final Users users = users();
        when(mockUserRepository.save(any(Users.class))).thenReturn(users);
        userServiceImplUnderTest.registerGoogleUser(request);
        verify(mockUserRepository).save(any(Users.class));
    }

    @Test
    public void testRegisterGoogleUser_UserRepositoryFindByEmailReturnsAbsent() {
        final RegisterGoogleRequest request = new RegisterGoogleRequest();
        request.setEmail("email");
        request.setRoleId(0L);
        final Optional<Role> optional = Optional.of(role());
        when(mockRoleService.findRoleById(0L)).thenReturn(optional);
        final Optional<Users> optional1 = Optional.of(users());
        when(mockUserRepository.findByUsername("username")).thenReturn(optional1);
        when(mockUserRepository.findByEmail("email")).thenReturn(Optional.empty());
        when(mockPasswordEncoder.encode("1")).thenReturn("password");
        final Users users = users();
        when(mockUserRepository.save(any(Users.class))).thenReturn(users);
        userServiceImplUnderTest.registerGoogleUser(request);
        verify(mockUserRepository).save(any(Users.class));
    }

    @Test
    public void testGetByUserName() {
        final Users users = users();
        when(mockUserRepository.getByUsername("username")).thenReturn(users);
        final Users result = userServiceImplUnderTest.getByUserName("username");

    }

    @Test
    public void testFindById() {
        final Users users =users();
        when(mockUserRepository.getUserById(0L)).thenReturn(users);
        final Users result = userServiceImplUnderTest.findById(0L);

    }

    @Test
    public void testFindByIdOp() {
        final Optional<Users> optional = Optional.of(users());
        when(mockUserRepository.findById(0L)).thenReturn(optional);
        final Optional<Users> result = userServiceImplUnderTest.findByIdOp(0L);

    }
    @Test
    public void testFindByIdOp_UserRepositoryReturnsAbsent() {
        when(mockUserRepository.findById(0L)).thenReturn(Optional.empty());
        final Optional<Users> result = userServiceImplUnderTest.findByIdOp(0L);
        assertThat(result).isEmpty();
    }

    @Test
    public void testFindAll() {
        final List<Users> users = Arrays.asList(users());
        when(mockUserRepository.findAll()).thenReturn(users);
        final List<Users> result = userServiceImplUnderTest.findAll();

    }

    @Test
    public void testFindAll_UserRepositoryReturnsNoItems() {
        when(mockUserRepository.findAll()).thenReturn(Collections.emptyList());
        final List<Users> result = userServiceImplUnderTest.findAll();
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testFindByPhoneNumber() {
        final Optional<Users> optional = Optional.of(users());
        when(mockUserRepository.findByPhone("phone")).thenReturn(optional);
        final Optional<Users> result = userServiceImplUnderTest.findByPhoneNumber("phone");

    }

    @Test
    public void testFindByPhoneNumber_UserRepositoryReturnsAbsent() {
        when(mockUserRepository.findByPhone("phone")).thenReturn(Optional.empty());
        final Optional<Users> result = userServiceImplUnderTest.findByPhoneNumber("phone");
        assertThat(result).isEmpty();
    }

    @Test
    public void testCountUser() {
        when(mockUserRepository.countUserRegisterToday()).thenReturn(Arrays.asList());
        when(mockUserRepository.countUserRegisterMonthAgo()).thenReturn(Arrays.asList());
        when(mockUserRepository.countAllUsersRegister()).thenReturn(Arrays.asList());
        final HashMap<String, List<CountRegisterUserResponse>> result = userServiceImplUnderTest.countUser();
    }

    @Test
    public void testCountUser_UserRepositoryCountUserRegisterTodayReturnsNoItems() {
        when(mockUserRepository.countUserRegisterToday()).thenReturn(Collections.emptyList());
        when(mockUserRepository.countUserRegisterMonthAgo()).thenReturn(Arrays.asList());
        when(mockUserRepository.countAllUsersRegister()).thenReturn(Arrays.asList());
        final HashMap<String, List<CountRegisterUserResponse>> result = userServiceImplUnderTest.countUser();
    }

    @Test
    public void testCountUser_UserRepositoryCountUserRegisterMonthAgoReturnsNoItems() {
        when(mockUserRepository.countUserRegisterToday()).thenReturn(Arrays.asList());
        when(mockUserRepository.countUserRegisterMonthAgo()).thenReturn(Collections.emptyList());
        when(mockUserRepository.countAllUsersRegister()).thenReturn(Arrays.asList());
        final HashMap<String, List<CountRegisterUserResponse>> result = userServiceImplUnderTest.countUser();
    }

    @Test
    public void testCountUser_UserRepositoryCountAllUsersRegisterReturnsNoItems() {
        when(mockUserRepository.countUserRegisterToday()).thenReturn(Arrays.asList());
        when(mockUserRepository.countUserRegisterMonthAgo()).thenReturn(Arrays.asList());
        when(mockUserRepository.countAllUsersRegister()).thenReturn(Collections.emptyList());
        final HashMap<String, List<CountRegisterUserResponse>> result = userServiceImplUnderTest.countUser();

    }

    @Test
    public void testLockUnlockUser() {
        final Users users = users();
        when(mockUserRepository.getById(0L)).thenReturn(users);
        final Users users1 = users();
        when(mockUserRepository.save(any(Users.class))).thenReturn(users1);
        final Users result = userServiceImplUnderTest.lockUnlockUser(0L);
        verify(mockUserRepository).save(any(Users.class));
    }

    @Test
    public void testLockUnlockUser_UserRepositoryGetByIdReturnsNull() {
        when(mockUserRepository.getById(0L)).thenReturn(null);
        assertThatThrownBy(() -> userServiceImplUnderTest.lockUnlockUser(0L)).isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testActiveDeactiveUser() {
        final Users users = users();
        when(mockUserRepository.getById(0L)).thenReturn(users);
        final Users users1 = users();
        when(mockUserRepository.save(any(Users.class))).thenReturn(users1);
        final Users result = userServiceImplUnderTest.activeDeactiveUser(0L);
        verify(mockUserRepository).save(any(Users.class));
    }

    @Test
    public void testActiveDeactiveUser_UserRepositoryGetByIdReturnsNull() {
        when(mockUserRepository.getById(0L)).thenReturn(null);
        assertThatThrownBy(() -> userServiceImplUnderTest.activeDeactiveUser(0L))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testUpdatePhoneNumber() {
        userServiceImplUnderTest.updatePhoneNumber("phoneNumber", 0L);
        verify(mockUserRepository).updatePhoneNumber("phoneNumber", 0L);
    }

    @Test
    public void testChangePassword() {
        final ChangePasswordRequest request = changePasswordRequest();
        final Optional<Users> optional = Optional.of(users());
        when(mockUserRepository.findByUsernameAndIsDeleted("userName", 0)).thenReturn(optional);
        when(mockPasswordEncoder.matches("rawPassword", "password")).thenReturn(false);
        when(mockPasswordEncoder.encode("rawPassword")).thenReturn("password");
        final Users users =users();
        when(mockUserRepository.save(any(Users.class))).thenReturn(users);
        userServiceImplUnderTest.changePassword("userName", request);

    }

    @Test
    public void testChangePassword_UserRepositoryFindByUsernameAndIsDeletedReturnsAbsent() {
        final ChangePasswordRequest request = changePasswordRequest();
        when(mockUserRepository.findByUsernameAndIsDeleted("userName", 0)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userServiceImplUnderTest.changePassword("userName", request))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testFindByEmail() {
        final Optional<Users> optional = Optional.of(users());
        when(mockUserRepository.findByEmail("email")).thenReturn(optional);
        final Users result = userServiceImplUnderTest.findByEmail("email");

    }

    @Test
    public void testFindByEmail_UserRepositoryReturnsAbsent() {
 
        when(mockUserRepository.findByEmail("email")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userServiceImplUnderTest.findByEmail("email"))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testFindByResetPasswordToken() {
        final Optional<Users> optional = Optional.of(users());
        when(mockUserRepository.findByResetPasswordToken("resetPasswordToken")).thenReturn(optional);
        final Users result = userServiceImplUnderTest.findByResetPasswordToken("resetPasswordToken");

    }

    @Test
    public void testFindByResetPasswordToken_UserRepositoryReturnsAbsent() {
        when(mockUserRepository.findByResetPasswordToken("resetPasswordToken")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userServiceImplUnderTest.findByResetPasswordToken("resetPasswordToken"))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testForgotPassword() throws Exception {
        final Optional<Users> optional = Optional.of(users());
        when(mockUserRepository.findByEmail("email")).thenReturn(optional);
        final Users users = users();
        when(mockUserRepository.save(any(Users.class))).thenReturn(users);
        userServiceImplUnderTest.forgotPassword("email");
        verify(mockUserRepository).save(any(Users.class));
        verify(mockEmailService).sendResetPasswordEmail("email", "link");
    }

    @Test
    public void testForgotPassword_UserRepositoryFindByEmailReturnsAbsent() {
        when(mockUserRepository.findByEmail("email")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userServiceImplUnderTest.forgotPassword("email")).isInstanceOf(Exception.class);
    }

    @Test
    public void testForgotPassword_EmailServiceThrowsMessagingException() throws Exception {
        final Optional<Users> optional = Optional.of(users());
        when(mockUserRepository.findByEmail("email")).thenReturn(optional);
        final Users users = users();
        when(mockUserRepository.save(any(Users.class))).thenReturn(users);
        doThrow(MessagingException.class).when(mockEmailService).sendResetPasswordEmail("email", "link");
        assertThatThrownBy(() -> userServiceImplUnderTest.forgotPassword("email")).isInstanceOf(Exception.class);
        verify(mockUserRepository).save(any(Users.class));
    }

    @Test
    public void testForgotPassword_EmailServiceThrowsUnsupportedEncodingException() throws Exception {
        final Optional<Users> optional = Optional.of(users());
        when(mockUserRepository.findByEmail("email")).thenReturn(optional);
        final Users users = users();
        when(mockUserRepository.save(any(Users.class))).thenReturn(users);
        doThrow(UnsupportedEncodingException.class).when(mockEmailService).sendResetPasswordEmail("email", "link");
        assertThatThrownBy(() -> userServiceImplUnderTest.forgotPassword("email")).isInstanceOf(Exception.class);
        verify(mockUserRepository).save(any(Users.class));
    }

    @Test
    public void testResetPassword() {
        final ResetPasswordRequest request = resetPasswordRequest();
        final Optional<Users> optional = Optional.of(users());
        when(mockUserRepository.findByResetPasswordToken("resetPasswordToken")).thenReturn(optional);
        when(mockPasswordEncoder.encode("rawPassword")).thenReturn("password");
        final Users users = users();
        when(mockUserRepository.save(any(Users.class))).thenReturn(users);
        userServiceImplUnderTest.resetPassword(request);

    }

    @Test
    public void testResetPassword_UserRepositoryFindByResetPasswordTokenReturnsAbsent() {
        final ResetPasswordRequest request = resetPasswordRequest();
        when(mockUserRepository.findByResetPasswordToken("resetPasswordToken")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userServiceImplUnderTest.resetPassword(request))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testUpdateIsVerifyPhone() {
        userServiceImplUnderTest.updateIsVerifyPhone(false, 0L);
        verify(mockUserRepository).updateIsVerifyPhone(false, 0L);
    }

    @Test
    public void testFindByPhoneAndIdIsNotIn() {
        final Users users = users();
        when(mockUserRepository.findByPhoneAndIdIsNotIn("phone", 0L)).thenReturn(users);
        final Users result = userServiceImplUnderTest.findByPhoneAndIdIsNotIn("phone", 0L);

    }

    @Test
    public void testUpdatePassword() {
        final Users user = users();
        final ResetPasswordRequest request = new ResetPasswordRequest("resetPasswordToken", "newPassword",
                "newPassword");
        when(mockPasswordEncoder.encode("rawPassword")).thenReturn("password");
        final Users users = users();
        when(mockUserRepository.save(any(Users.class))).thenReturn(users);
        userServiceImplUnderTest.updatePassword(user, request);

    }
}
