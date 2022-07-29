package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE public.banner SET rental_package_id=?1, title=?2, description=?3, image=?4, spotlight=?5, homepage_banner_a=?6, homepage_banner_b=?7, homepage_banner_c=?8, job_banner_a=?8, job_banner_b=?10, job_banner_c=?11, is_deleted=?12, updated_at=?13, price=?14, discount=?15 WHERE id=?16", nativeQuery = true)
    void updateBanner(long retalPackageId, String title, String description, String image, boolean isSpotlight, boolean isHomePageBannerA, boolean isHomePageBannerB, boolean isHomePageBannerC, boolean isJobBannerA, boolean isJobBannerB, boolean isJobBannerC, boolean isDeleted, LocalDateTime updatedAt, long price, long discount, long id);

    @Query(value = "Select * from banner b where (?1 = true) and b.created_at > ?2 and b.created_at <?3", nativeQuery = true)
    List<Banner> searchWithfilter(boolean screen, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "select * from banner b where lower(b.title) = lower(?)", nativeQuery = true)
    List<Banner> getBannersByTitle(String title);
}
