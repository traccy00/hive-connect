package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.RentalPackage;

import java.util.List;

public interface RentalPackageService {
    List<RentalPackage> findAll();

    boolean existById(long id);

    boolean existByName(String groupName);

    void saveRentalPackage(RentalPackage rentalPackage);
}
