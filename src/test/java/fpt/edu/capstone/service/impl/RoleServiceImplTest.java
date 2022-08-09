package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.common.CreateRoleUserRequest;
import fpt.edu.capstone.entity.Role;
import fpt.edu.capstone.entity.WorkExperience;
import fpt.edu.capstone.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
public class RoleServiceImplTest {
    @InjectMocks
    RoleServiceImpl roleService;

    @Mock
    RoleRepository roleRepository;

    @Test
    void getRoleByIdTest(){
        Role role = new Role();
        role.setId(1L);
        role.setName("Recruiter");
        role.setDescription("");
        Mockito.when(roleRepository.getById(ArgumentMatchers.anyLong())).thenReturn(role);
        Role role1 = roleService.getRoleById(1L);
        assertEquals(1L, role1.getId());
    }

    @Test
    void findRoleByIdTest(){
        Role role = new Role();
        role.setId(1L);
        role.setName("Recruiter");
        role.setDescription("");
        Mockito.when(roleRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(role));
        Optional<Role> role1 = roleService.findRoleById(1L);
        assertEquals(1L, role1.get().getId());
    }

    @Test
    void createRoleTest(){
        Role role = new Role();
        role.setId(111L);
        role.setName("Recruiter");
        role.setDescription("");

        CreateRoleUserRequest roleRequest = new CreateRoleUserRequest();
        roleRequest.setRoleName("Recruiter");
        roleRequest.setDescription("");
        Mockito.when(roleRepository.save(ArgumentMatchers.any(Role.class))).thenReturn(role);
        Role role1 = roleService.createRole(roleRequest);
        assertEquals(0, role1.getId());
    }


    @Test
    void getAllRoleTest(){
        List<Role> list = new ArrayList<>();
        Role role = new Role();
        role.setId(111L);
        role.setName("Recruiter");
        role.setDescription("");
        list.add(role);
        Mockito.when(roleRepository.findAll()).thenReturn(list);
        List <Role> roleList = roleService.getAllRole();
        assertEquals(1, roleList.size());
    }
}
