package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.Users;
import fpt.edu.capstone.repository.UserRepository;
import fpt.edu.capstone.service.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
public class SecurityUserServiceImplTest {
    @InjectMocks
    SecurityUserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleService roleService;

    @Test
    void loadUserByUsernameTest(){
        Users users = new Users();
        users.setUsername("namnh");
        users.setPassword("abc");
        users.setId(1L);
        users.setRoleId(123L);
        Mockito.when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.of(users));
        userService.loadUserByUsername("namnh");

//        assertEquals("namnh", userService.lo);
    }
}
