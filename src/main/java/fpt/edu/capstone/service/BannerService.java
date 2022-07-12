package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.Banner;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BannerService {
    List<Banner> getAllBanner();

    void updateBanner(Banner newBanner);

    Banner insertBanner(Banner banner);

    void deleteBanner(long bannerId);

    Optional<Banner> findById(long bannerId);

    List<Banner> searchByFiler(boolean screen, LocalDateTime startDate, LocalDateTime endDate);
}
