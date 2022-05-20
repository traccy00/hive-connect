package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.sprint1.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query(value = "select * from companies where id = ?", nativeQuery = true)
    Company getCompanyById(long companyId);
}
