package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query(value = "select * from companies where id = ?", nativeQuery = true)
    Company getCompanyById(long companyId);

    @Query(value = "select * from companies where name = ?1", nativeQuery = true)
    Optional<Company> getCompanyByName(String companyName);

    @Query(value = "select * from companies where taxcode = ?1", nativeQuery = true)
    Optional<Company> getCompanyByTaxcode(String taxCode);
}
