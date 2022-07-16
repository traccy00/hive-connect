package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.DetailPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DetailPackageRepository extends JpaRepository<DetailPackage, Long> {
    @Query(value = "select d from DetailPackage d where (lower(d.type) like lower(concat('%', :name, '%')) or :name is null or :name ='') " +
            "and d.rentalPackageId =:rentalId or 0 =:rentalId " +
            "and d.isDeleted = false ")
    List<DetailPackage> getListFilter(@Param("name") String name,@Param("rentalId") long rentalId);

    Optional<DetailPackage> findByType(String type);
}