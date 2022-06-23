package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.OtherSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface OtherSkillReposiroty extends JpaRepository<OtherSkill,Long> {
    @Query(value = "select * from other_skill o where o.cv_id = ?1", nativeQuery = true)
    List<OtherSkill> getListOtherSkillByCvId(Long cvId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO other_skill (skill_name, cv_id, level) VALUES(?1, ?2, ?3)", nativeQuery = true)
    void insertOtherSkill(String skillName, long cvId, String level);
}
