package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.Avatar;
import fpt.edu.capstone.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, String> {

    @Query(value = "select * from image where company_id = ?1 and is_avatar = true", nativeQuery = true)
    Optional<Image> findAvatarByCompanyId(long companyId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE image SET data=?1, is_avatar = ?2 WHERE id=?3", nativeQuery = true)
    void updateCompanyAvatar(byte[] data,boolean isAvatar, String imageId);
}
