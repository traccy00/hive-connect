package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.sprint1.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);

    Users findByEmail(String email);

    Optional <Users> findByUsernameAndIsDeleted(String account, int isDelete);

    @Query(value = "SELECT u FROM Users u where u.username =:username or u.email =:email")
    Optional <Users> checkExistedUserByUsernameOrEmail(@Param("username") String username,@Param("email") String email);
}