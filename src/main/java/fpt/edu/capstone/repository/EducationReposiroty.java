package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EducationReposiroty extends JpaRepository<Education,Long> {
    @Query(value = "select * from Education e where e.cv_id = ?1", nativeQuery = true)
    List<Education> getListEducationByCvId(Long cvId);
}
