package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.banner.ConfigBannerRequest;
import fpt.edu.capstone.dto.banner.UpdateBannerRequest;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.entity.Banner;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.BannerRepository;
import fpt.edu.capstone.service.BannerService;
import fpt.edu.capstone.service.RentalPackageService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseDataPagination;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        Optional<Banner> banner = bannerRepository.findById(bannerId);
        if(!banner.isPresent()) {
            throw new HiveConnectException(ResponseMessageConstants.DETAIL_PACKAGE_DOES_NOT_EXIST);
        }
        banner.get().setDeleted(true);
        bannerRepository.save(banner.get());
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
//        if(request.getImage() == null || request.getImage().trim().isEmpty()) {
//            throw new HiveConnectException(ResponseMessageConstants.BANNER_IMAGE_INVALID);
//        }
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

    @Override
    public ResponseDataPagination getBannerByFilter(Integer pageNo, Integer pageSize, String title, boolean isDeleted) {
        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);
        Page<Banner> bannerPage = bannerRepository.getBannerByFilter(pageable, title,3,  isDeleted);
        List <Banner> bannerList = bannerPage.getContent();

        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();
        responseDataPagination.setData(bannerList);
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(bannerPage.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(bannerPage.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);
        return responseDataPagination;
    }

    @Override
    public Page<Banner> getListFilter(Pageable pageable, String name, long rentalId, boolean isDeleted) {
        return bannerRepository.getBannerByFilter(pageable, name, rentalId, isDeleted);
    }

    @Override
    public Banner findByRentalPackageIdAndId(long rentalPackageId, long id) {
        return bannerRepository.findByRentalPackageIdAndId(rentalPackageId, id);
    }
}
