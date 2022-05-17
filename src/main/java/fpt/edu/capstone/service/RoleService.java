package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.sprint1.Role;

import java.util.Optional;

public interface RoleService {
    Role getRoleById(long roleId);

    Optional<Role> findRoleById(long roleId);
}
