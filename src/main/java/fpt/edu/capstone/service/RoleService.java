package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.Role;

import java.util.Optional;

public interface RoleService {
    Role getRoleById(long roleId);

    Optional<Role> findRoleById(long roleId);
}
