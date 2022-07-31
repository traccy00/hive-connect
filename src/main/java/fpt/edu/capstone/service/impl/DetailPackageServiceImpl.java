package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.DetailPackage;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.DetailPackageRepository;
import fpt.edu.capstone.service.DetailPackageService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseDataPagination;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DetailPackageServiceImpl implements DetailPackageService {
    private final DetailPackageRepository detailPackageRepository;
    @Override
    public ResponseDataPagination getListDetailPackageFilter(Integer pageNo, Integer pageSize, String name, long rentalId,boolean isDeleted) {
        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);
        Page<DetailPackage> packagePage =  detailPackageRepository.getListFilter(pageable, name, rentalId, isDeleted);

        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();
        responseDataPagination.setData(packagePage.toList());
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(packagePage.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(packagePage.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);
        return responseDataPagination;
    }

    @Override
    public DetailPackage findById(long id) {
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
        DetailPackage dp = detailPackageRepository.getById(detailPackage.getId());
        if(dp == null){
            throw new HiveConnectException("Gói dịch vụ không tồn tại");
        }
        detailPackageRepository.saveAndFlush(detailPackage);
    }

    @Override
    public String findNameById(long id) {
        return detailPackageRepository.findDetailNameById(id);
    }
}
