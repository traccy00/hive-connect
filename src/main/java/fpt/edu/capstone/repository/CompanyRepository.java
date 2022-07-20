package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query(value = "select * from companies where id = ?", nativeQuery = true)
    Company getCompanyById(long companyId);

    @Query(value = "select * from companies where lower(name) = lower(?) and is_deleted = 0 limit 1;", nativeQuery = true)
    Optional<Company> getCompanyByName(String companyName);

    @Query(value = "select * from companies where taxcode = ?1", nativeQuery = true)
    Optional<Company> getCompanyByTaxcode(String taxCode);

    @Transactional
    @Modifying
    @Query(value = "Update companies set avatar = ?1 where id = ?2", nativeQuery = true)
    void updateCompanyAvatarUrl(String avatarId, long companyId);

    @Query(value = "select * from companies c where name like concat('%',:companyName,'%')", nativeQuery = true)
    List<Company> getAllByName(@Param("companyName") String companyName);
}
