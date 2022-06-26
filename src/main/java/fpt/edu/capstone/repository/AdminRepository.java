package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Query("select a from Admin a")
    Page<Admin> getListAdminByFilter(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO public.admin (user_id, full_name) VALUES(?1, null)", nativeQuery = true)
    void insertAdmin(long userId);

    Optional <Admin> findByUserId(long userId);
}
