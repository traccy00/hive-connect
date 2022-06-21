package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.Role;
import fpt.edu.capstone.repository.RoleRepository;
import fpt.edu.capstone.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRoleById(long roleId) {
        return roleRepository.getById(roleId);
    }

    @Override
    public Optional<Role> findRoleById(long roleId) {
        return roleRepository.findById(roleId);
    }
}
