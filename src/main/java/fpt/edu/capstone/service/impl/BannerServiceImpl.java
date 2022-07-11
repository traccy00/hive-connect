package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.Banner;
import fpt.edu.capstone.repository.BannerRepository;
import fpt.edu.capstone.service.BannerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public List<Banner> getAllBanner() {
        return bannerRepository.findAll();
    }

    @Override
    public Banner insertBanner(Banner banner) {
        return bannerRepository.save(banner);
    }

    @Override
    public void updateBanner(Banner newBanner) {
        bannerRepository.updateBanner(newBanner.getRentalPackageId(), newBanner.getTitle(), newBanner.getDescription(), newBanner.getImage(), newBanner.isSpotlight(), newBanner.isHomepageBannerA(), newBanner.isHomePageBannerB(), newBanner.isHomePageBannerC(), newBanner.isJobBannerA(), newBanner.isJobBannerB(), newBanner.isJobBannerC(), newBanner.isDeleted(), LocalDateTime.now(), newBanner.getPrice(), newBanner.getDiscount(), newBanner.getId());
    }

    @Override
    public void deleteBanner(long bannerId) {

    }

    @Override
    public Optional<Banner> findById(long bannerId) {
        return bannerRepository.findById(bannerId);
    }

    @Override
    public List<Banner> searchByFiler(boolean screen, LocalDateTime startDate, LocalDateTime endDate) {
        return bannerRepository.searchWithfilter(screen, startDate, endDate);
    }

}
