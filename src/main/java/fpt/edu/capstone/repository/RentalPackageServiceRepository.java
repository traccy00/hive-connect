package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.RentalPackage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalPackageServiceRepository extends JpaRepository<RentalPackage, Long> {
    boolean existsByPackageGroup(String g);
}
