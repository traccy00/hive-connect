package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.DetailPackage;

import java.util.List;
import java.util.Optional;

public interface DetailPackageService {
    List<DetailPackage> getListDetailPackageFilter(String name, long rentalId);

    DetailPackage findById(long id);

    Optional<DetailPackage> findByName(String name);

    void saveDetailPackage(DetailPackage detailPackage);

    void updateDetailPackage(DetailPackage detailPackage);

    String findNameById(long id);
}
