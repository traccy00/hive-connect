package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.banner.ConfigBannerRequest;
import fpt.edu.capstone.dto.banner.UpdateBannerRequest;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.entity.Banner;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.BannerRepository;
import fpt.edu.capstone.service.BannerService;
import fpt.edu.capstone.service.RentalPackageService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final ModelMapper modelMapper;

    private final BannerRepository bannerRepository;

    private final RentalPackageService rentalPackageService;

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
    public Banner findById(long bannerId) {
        if(!bannerRepository.findById(bannerId).isPresent()) {
            throw new HiveConnectException(ResponseMessageConstants.DETAIL_PACKAGE_DOES_NOT_EXIST);
        }
        return bannerRepository.findById(bannerId).get();
    }

    @Override
    public List<Banner> searchByFiler(boolean screen, LocalDateTime startDate, LocalDateTime endDate) {
        return bannerRepository.searchWithfilter(screen, startDate, endDate);
    }

    @Override
    public Banner insertBanner(ConfigBannerRequest request) {
        if(request.getPrice() < 0) {
            throw new HiveConnectException(ResponseMessageConstants.PRICE_EQUAL_GREATER_THAN_ZERO);
        }
        if(request.getDiscount() < 0) {
            throw new HiveConnectException(ResponseMessageConstants.PRICE_EQUAL_GREATER_THAN_ZERO);
        }
        if(request.getDiscount() > request.getPrice()) {
            throw new HiveConnectException(ResponseMessageConstants.DISCOUNT_PRICE_INVALID);
        }
        if(request.getImage() == null || request.getImage().trim().isEmpty()) {
            throw new HiveConnectException(ResponseMessageConstants.BANNER_IMAGE_INVALID);
        }
        if(request.isSpotlight() == false
                && request.isHomepageBannerA() == false && request.isHomePageBannerB() == false && request.isHomePageBannerC() == false
                && request.isJobBannerA() == false && request.isJobBannerB() == false && request.isJobBannerC() == false) {
            throw new HiveConnectException(ResponseMessageConstants.BANNER_POSITION_INVALID);
        }
        if(!rentalPackageService.findById(request.getRentalPackageId()).isPresent()) {
            throw new HiveConnectException(ResponseMessageConstants.RENTAL_PACKAGE_DOES_NOT_EXIST);
        }
        List<Banner> bannersByTitle = bannerRepository.getBannersByTitle(request.getTitle());
        if(bannersByTitle != null && !bannersByTitle.isEmpty()) {
            throw new HiveConnectException(ResponseMessageConstants.BANNER_TITLE_HAVE_ALREADY_EXISTED);
        }
        Banner banner = modelMapper.map(request, Banner.class);
        banner.create();
        banner.setDeleted(false);
        bannerRepository.save(banner);
        if(!bannerRepository.findById(banner.getId()).isPresent()) {
            throw new HiveConnectException(ResponseMessageConstants.CREATE_BANNER_FAIL);
        }
        return banner;
    }

    @Override
    public void updateBanner(UpdateBannerRequest request) {
        Optional<Banner> detailBannerPackage = bannerRepository.findById(request.getBannerId());
        if(!detailBannerPackage.isPresent()) {
            throw new HiveConnectException(ResponseMessageConstants.DETAIL_PACKAGE_DOES_NOT_EXIST);
        }
        bannerRepository.save(detailBannerPackage.get());
    }
}
