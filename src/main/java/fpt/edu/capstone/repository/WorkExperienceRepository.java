package fpt.edu.capstone.repository;

import fpt.edu.capstone.atmpCandidate.MajorLevel;
import fpt.edu.capstone.atmpCandidate.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience,Long> {
    @Query(value = "select * from work_experience w where w.cv_id = ?1", nativeQuery = true)
    List<WorkExperience> getListWorkExperienceByCvId(Long cvId);
}
