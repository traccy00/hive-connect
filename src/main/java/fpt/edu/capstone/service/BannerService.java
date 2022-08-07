package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.banner.ConfigBannerRequest;
import fpt.edu.capstone.dto.banner.UpdateBannerRequest;
import fpt.edu.capstone.entity.Banner;
import fpt.edu.capstone.utils.ResponseDataPagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BannerService {
    List<Banner> getAllBanner();

    void updateBanner(Banner newBanner);

    Banner insertBanner(Banner banner);

    void deleteBanner(long bannerId);

    Banner findById(long bannerId);

    List<Banner> searchByFiler(boolean screen, LocalDateTime startDate, LocalDateTime endDate);

    Banner insertBanner(ConfigBannerRequest request);

    void updateBanner(UpdateBannerRequest request);

    ResponseDataPagination getBannerByFilter(Integer pageNo, Integer pageSize, String title, boolean isDeleted);

    Page<Banner> getListFilter(Pageable pageable, @Param("name") String name,
                                      @Param("rentalId") long rentalId, @Param("status") boolean isDeleted);

    Banner findByRentalPackageIdAndId(long rentalPackageId, long id);

}
