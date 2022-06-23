package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface EducationReposiroty extends JpaRepository<Education,Long> {
    @Query(value = "select * from Education e where e.cv_id = ?1", nativeQuery = true)
    List<Education> getListEducationByCvId(Long cvId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO public.education (cv_id, school, major, start_date, end_date, description, is_studying) VALUES(?1, ?2, ?3, ?4, ?5, ?6, ?7)", nativeQuery = true)
    void insertEducation(long cvId, String school, String major, LocalDateTime startDate, LocalDateTime endDate, String description, boolean isStudying);

}
