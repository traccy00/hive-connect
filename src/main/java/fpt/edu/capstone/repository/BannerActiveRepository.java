package fpt.edu.capstone.repository;

import fpt.edu.capstone.dto.recruiter.BannerPositionDetailResponse;
import fpt.edu.capstone.entity.BannerActive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerActiveRepository extends JpaRepository<BannerActive, Long> {

    @Query(value = "select * from banner_active ba where ba.display_position = ?", nativeQuery = true)
    List<BannerActive> getBannerByPosition(String displayPosition);

    List<BannerActive> getAllByPaymentId(long paymentId);

    @Query(value = "select display_position as displayPosition, image_url as imageUrl, approval_status as approvalStatus " +
            "from banner_active ba " +
            "where payment_id = ?1", nativeQuery = true)
    List<BannerPositionDetailResponse> getBannerActiveByPaymentId(long paymentId);
}
