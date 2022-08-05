package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.detail_package.CreateOpenCvPackageRequest;
import fpt.edu.capstone.entity.DetailPackage;
import fpt.edu.capstone.utils.ResponseDataPagination;

import java.util.List;
import java.util.Optional;

public interface DetailPackageService {
    ResponseDataPagination getListDetailPackageFilter(Integer pageNo, Integer pageSize, String name, long rentalId, boolean isDeleted);

    DetailPackage findById(long id);

    Optional<DetailPackage> findByName(String name);

    void saveDetailPackage(DetailPackage detailPackage);

    void updateDetailPackage(DetailPackage detailPackage);

    String findNameById(long id);

    void createOpenCvPackage(CreateOpenCvPackageRequest request);

    void deleteDetailPackage(long detailPackageId);
}
