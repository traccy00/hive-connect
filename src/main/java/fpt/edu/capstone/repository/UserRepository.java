package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.sprint1.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    User findByEmail(String email);

    Optional <User> findByUsernameAndIsDeleted(String account, int isDelete);
}