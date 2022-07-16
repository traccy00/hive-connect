package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.DetailPackage;
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
    public List<DetailPackage> getListDetailPackageFilter(String name) {
        return detailPackageRepository.getListFilter(name);
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
}
