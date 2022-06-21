package fpt.edu.capstone.repository;

import fpt.edu.capstone.atmpCandidate.Language;
import fpt.edu.capstone.atmpCandidate.OtherSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OtherSkillReposiroty extends JpaRepository<OtherSkill,Long> {
    @Query(value = "select * from other_skill o where o.cv_id = ?1", nativeQuery = true)
    List<OtherSkill> getListOtherSkillByCvId(Long cvId);
}
