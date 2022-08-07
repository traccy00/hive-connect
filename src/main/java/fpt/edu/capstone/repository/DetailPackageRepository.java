package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.DetailPackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DetailPackageRepository extends JpaRepository<DetailPackage, Long> {
    @Query(value = "select d from DetailPackage d where " +
            "(lower(d.detailName) like lower(concat('%', :name, '%')) or :name is null or :name ='') " +
            "and d.rentalPackageId =:rentalId or 0 =:rentalId " +
            "and (d.isDeleted =:status or :status is null) ")
    Page<DetailPackage> getListFilter(Pageable pageable, @Param("name") String name,
                                      @Param("rentalId") long rentalId, @Param("status") boolean isDeleted);

    @Query(value = "select * from detail_package dp where lower(dp.detail_name) = lower(:detailName)", nativeQuery = true)
    Optional<DetailPackage> findByDetailName(@Param("detailName") String detailName);

    @Query(value = "select dp.detailName from DetailPackage dp where dp.id =:id")
    String findDetailNameById (@Param("id") long id);

    DetailPackage findByRentalPackageIdAndId(long rentalPackageId, long id);

}
