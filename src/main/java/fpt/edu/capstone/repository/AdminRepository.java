package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.sprint1.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Query("select a from Admin a")
    Page<Admin> getListAdminByFilter(Pageable pageable);
}
