package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.detail_package.CreateOpenCvPackageRequest;
import fpt.edu.capstone.dto.detail_package.DetailPackageResponse;
import fpt.edu.capstone.dto.rental_package.RentalPackageResponse;
import fpt.edu.capstone.entity.Banner;
import fpt.edu.capstone.entity.DetailPackage;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.DetailPackageRepository;
import fpt.edu.capstone.service.BannerService;
import fpt.edu.capstone.service.DetailPackageService;
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

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DetailPackageServiceImpl implements DetailPackageService {

    private final ModelMapper modelMapper;

    private final DetailPackageRepository detailPackageRepository;

    private final RentalPackageService rentalPackageService;

    private final BannerService bannerService;

    @Override
    public ResponseDataPagination getListDetailPackageFilter(Integer pageNo, Integer pageSize, String name, long rentalId, boolean isDeleted) {
        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);

        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();

        if(rentalId == 0){
            RentalPackageResponse packageResponse = new RentalPackageResponse();
            Page<DetailPackage> packagePage = detailPackageRepository.getListFilter(pageable, name, rentalId, isDeleted);
            Page <Banner> bannerPage = bannerService.getListFilter(pageable, name, rentalId, isDeleted);

            packageResponse.setDetailPackage(packagePage.getContent());
            packageResponse.setBanner(bannerPage.getContent());

            long totalPage = packagePage.getTotalPages() + bannerPage.getTotalPages()-1;
            int totalRecords = Integer.parseInt(String.valueOf(packagePage.getTotalElements())) +
                    Integer.parseInt(String.valueOf(bannerPage.getTotalElements()));

            responseDataPagination.setData(packageResponse);
            pagination.setCurrentPage(pageNo);
            pagination.setPageSize(pageSize);
            pagination.setTotalPage(totalPage);
            pagination.setTotalRecords(totalRecords);
            responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
            responseDataPagination.setPagination(pagination);

        }

        if(rentalId == 1 || rentalId == 2 ){
            Page<DetailPackage> packagePage = detailPackageRepository.getListFilter(pageable, name, rentalId, isDeleted);
            responseDataPagination.setData(packagePage.toList());
            pagination.setCurrentPage(pageNo);
            pagination.setPageSize(pageSize);
            pagination.setTotalPage(packagePage.getTotalPages());
            pagination.setTotalRecords(Integer.parseInt(String.valueOf(packagePage.getTotalElements())));
            responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
            responseDataPagination.setPagination(pagination);
        }
        if(rentalId == 3){
            Page <Banner> bannerPage = bannerService.getListFilter(pageable, name, rentalId, isDeleted);
            responseDataPagination.setData(bannerPage.toList());
            pagination.setCurrentPage(pageNo);
            pagination.setPageSize(pageSize);
            pagination.setTotalPage(bannerPage.getTotalPages());
            pagination.setTotalRecords(Integer.parseInt(String.valueOf(bannerPage.getTotalElements())));
            responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
            responseDataPagination.setPagination(pagination);
        }
        return responseDataPagination;
    }

    @Override
    public DetailPackage findById(long id) {
        if (!detailPackageRepository.findById(id).isPresent()) {
            throw new HiveConnectException(ResponseMessageConstants.DETAIL_PACKAGE_DOES_NOT_EXIST);
        }
        return detailPackageRepository.findById(id).get();
    }

    @Override
    public Optional<DetailPackage> findByName(String name) {
        return detailPackageRepository.findByDetailName(name);
    }

    @Override
    public void saveDetailPackage(DetailPackage detailPackage) {
        detailPackageRepository.save(detailPackage);
    }

    @Override
    public void updateDetailPackage(DetailPackage detailPackage) {
        findById(detailPackage.getId());
        detailPackageRepository.saveAndFlush(detailPackage);
    }

    @Override
    public String findNameById(long id) {
        return detailPackageRepository.findDetailNameById(id);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void createOpenCvPackage(CreateOpenCvPackageRequest request) {
        if (!rentalPackageService.existById(request.getRentalPackageId())) {
            throw new HiveConnectException(ResponseMessageConstants.PAYMENT_DOES_NOT_EXIST);
        }
        DetailPackage detailPackage = modelMapper.map(request, DetailPackage.class);
        detailPackage.create();
        detailPackageRepository.save(detailPackage);
        if(!detailPackageRepository.findById(detailPackage.getId()).isPresent()) {
            throw new HiveConnectException(ResponseMessageConstants.CREATE_FAIL);
        }
    }

    @Override
    public void deleteDetailPackage(long detailPackageId) {
        Optional<DetailPackage> detailPackage = detailPackageRepository.findById(detailPackageId);
        if(!detailPackage.isPresent()) {
            throw new HiveConnectException(ResponseMessageConstants.DETAIL_PACKAGE_DOES_NOT_EXIST);
        }
        detailPackage.get().setDeleted(true);
        detailPackageRepository.save(detailPackage.get());
    }

    @Override
    public DetailPackageResponse getDetailPackageInfor(long groupPackageId, long packageId) {
        DetailPackageResponse response = new DetailPackageResponse();
        DetailPackage normalPackage = detailPackageRepository.findByRentalPackageIdAndId(groupPackageId, packageId);
        Banner bannerPackage = bannerService.findByRentalPackageIdAndId(groupPackageId, packageId);
        response.setNormalPackage(normalPackage);
        response.setBannerPackage(bannerPackage);
        return response;
    }
}
