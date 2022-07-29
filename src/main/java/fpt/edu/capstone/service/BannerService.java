package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.banner.ConfigBannerRequest;
import fpt.edu.capstone.dto.banner.UpdateBannerRequest;
import fpt.edu.capstone.entity.Banner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BannerService {
    List<Banner> getAllBanner();

    void updateBanner(Banner newBanner);

    Banner insertBanner(Banner banner);

    void deleteBanner(long bannerId);

    Banner findById(long bannerId);

    List<Banner> searchByFiler(boolean screen, LocalDateTime startDate, LocalDateTime endDate);

    Banner insertBanner(ConfigBannerRequest request);

    void updateBanner(UpdateBannerRequest request);
}
