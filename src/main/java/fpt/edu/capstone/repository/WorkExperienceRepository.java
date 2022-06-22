package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience,Long> {
    @Query(value = "select * from work_experience w where w.cv_id = ?1", nativeQuery = true)
    List<WorkExperience> getListWorkExperienceByCvId(Long cvId);

    @Modifying
    @Transactional
    @Query( value = "INSERT INTO public.work_experience (cv_id, company_name, position, start_date, end_date, description) VALUES(?1, ?2, ?3, ?4, ?5, ?6)", nativeQuery = true)
    void insertWorkExperience(long cvId, String companyName, String position, LocalDateTime startDate, LocalDateTime endDate, String description);
}
