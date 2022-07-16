package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.DetailPackage;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.DetailPackageRepository;
import fpt.edu.capstone.service.DetailPackageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DetailPackageServiceImpl implements DetailPackageService {
    private final DetailPackageRepository detailPackageRepository;
    @Override
    public List<DetailPackage> getListDetailPackageFilter(String name, long rentalId) {
        return detailPackageRepository.getListFilter(name, rentalId);
    }

    @Override
    public DetailPackage findById(long id) {
        return detailPackageRepository.findById(id).get();
    }

    @Override
    public Optional<DetailPackage> findByName(String name) {
        return detailPackageRepository.findByType(name);
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
}
