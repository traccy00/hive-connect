package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(value = "select * from image where company_id = ?1 and is_avatar = true", nativeQuery = true)
    Optional<Image> findAvatarByCompanyId(long companyId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE image SET data=?1, is_avatar = ?2 WHERE id=?3", nativeQuery = true)
    void updateCompanyAvatar(byte[] data,boolean isAvatar, String imageId);

    @Query(value = "select * from image i where i.recruiter_id = ? and i.is_avatar = true", nativeQuery = true)
    Image getAvatarRecruiter(long recruiterId);

    @Query(value = "select * from image i where i.candidate_id = ? and i.is_avatar = true", nativeQuery = true)
    Image getAvatarCandidate(long candidateId);
}
