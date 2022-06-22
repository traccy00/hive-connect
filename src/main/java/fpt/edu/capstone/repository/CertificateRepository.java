package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    @Query(value = "select * from Certificate c where c.cv_id = ?1", nativeQuery = true)
    List<Certificate> getListCertificateByCvId(Long cvId);
}
