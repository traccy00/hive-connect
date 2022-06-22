package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "select * from roles where id = ?", nativeQuery = true)
    Role getById(long roleId);
}
