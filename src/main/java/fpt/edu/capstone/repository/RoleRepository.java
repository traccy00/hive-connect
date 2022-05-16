package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.sprint1.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findById(long roleId);
}
