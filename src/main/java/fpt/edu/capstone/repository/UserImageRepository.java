package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserImageRepository extends JpaRepository<Avatar, String> {

    @Query(value = "select * from avatar where user_id = ?1", nativeQuery = true)
    Optional<Avatar> findAvatarByUserId(long user_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE avatar SET data=?1 WHERE id=?2", nativeQuery = true)
    void updateAvatar(byte[] data, String id);

}
