package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.admin.user.AdminManageResponse;
import fpt.edu.capstone.entity.Admin;
import fpt.edu.capstone.entity.Users;
import fpt.edu.capstone.repository.AdminRepository;
import fpt.edu.capstone.service.UserService;
import fpt.edu.capstone.utils.ResponseDataPagination;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceImplTest {

    @Mock
    private AdminRepository mockAdminRepository;
    @Mock
    private UserService mockUserService;

    private AdminServiceImpl adminServiceImplUnderTest;

    @Before
    public void setUp() throws Exception {
        adminServiceImplUnderTest = new AdminServiceImpl(mockAdminRepository, mockUserService);
    }

    private Admin admin(){
        Admin admin = new Admin(1L, 1L, "fullName",
                new Users(1L, "username", "password", "email", "phone", 1L, 0, LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        false, false, false, false, "avatar", "resetPasswordToken", false));
        return admin;
    }

    private Users users(){
        Users users = new Users();
        users.setId(1L);
        users.setUsername("username");
        users.setPassword("password");
        users.setEmail("email");
        users.setPhone("0967445450");
        users.setRoleId(1L);
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
    @Test
    public void testGetListAdmin() throws Exception {
        final Page<Admin> admins = new PageImpl<>(Arrays.asList(admin()));
        when(mockAdminRepository.getListAdminByFilter(any(Pageable.class))).thenReturn(admins);
        final Users users = users();
        when(mockUserService.getUserById(1L)).thenReturn(users);
        final ResponseDataPagination result = adminServiceImplUnderTest.getListAdmin(1, 10);
    }

    @Test
    public void testGetListAdmin_AdminRepositoryReturnsNoItems() throws Exception {
        when(mockAdminRepository.getListAdminByFilter(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));
        final Users users = users();
        when(mockUserService.getUserById(1L)).thenReturn(users);
        final ResponseDataPagination result = adminServiceImplUnderTest.getListAdmin(1, 10);
    }

    @Test
    public void testGetListAdmin_ThrowsException() {
        final Page<Admin> admins = new PageImpl<>(Arrays.asList(admin()));
        when(mockAdminRepository.getListAdminByFilter(any(Pageable.class))).thenReturn(admins);
        final Users users = users();
        when(mockUserService.getUserById(1L)).thenReturn(users);
        assertThatThrownBy(() -> adminServiceImplUnderTest.getListAdmin(0, 0)).isInstanceOf(Exception.class);
    }

    @Test
    public void testFindAdminByUserId() {
        final Optional<Admin> admin = Optional.of(admin());
        when(mockAdminRepository.findByUserId(1L)).thenReturn(admin);
        final Optional<Admin> result = adminServiceImplUnderTest.findAdminByUserId(1L);
    }

    @Test
    public void testFindAdminByUserId_AdminRepositoryReturnsAbsent() {
        when(mockAdminRepository.findByUserId(1L)).thenReturn(Optional.empty());
        final Optional<Admin> result = adminServiceImplUnderTest.findAdminByUserId(1L);
        assertThat(result).isEmpty();
    }

    @Test
    public void testInsertAdmin() {
        adminServiceImplUnderTest.insertAdmin(1L);
        verify(mockAdminRepository).insertAdmin(1L);
    }

    @Test
    public void testSearchAdmins() {
        when(mockAdminRepository.searchAdmin(any(Pageable.class), eq("username"), eq("email")))
                .thenReturn(new PageImpl<>(Arrays.asList()));
        final Page<AdminManageResponse> result = adminServiceImplUnderTest.searchAdmins(PageRequest.of(0, 1),
                "username", "email");
    }

    @Test
    public void testSearchAdmins_AdminRepositoryReturnsNoItems() {
        when(mockAdminRepository.searchAdmin(any(Pageable.class), eq("username"), eq("email")))
                .thenReturn(new PageImpl<>(Collections.emptyList()));
        final Page<AdminManageResponse> result = adminServiceImplUnderTest.searchAdmins(PageRequest.of(0, 1),
                "username", "email");
    }
}
