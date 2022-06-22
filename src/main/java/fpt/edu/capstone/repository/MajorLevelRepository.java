package fpt.edu.capstone.repository;


import fpt.edu.capstone.entity.MajorLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MajorLevelRepository extends JpaRepository<MajorLevel,Long> {
    @Query(value = "select * from major_level m where m.cv_id = ?1", nativeQuery = true)
    List<MajorLevel> getListMajorLevelByCvId(Long cvId);

    @Query(value = "INSERT INTO public.major_level(field_id, major_id, cv_id, level, status) VALUES(?1, ?2, ?3, '?4', ?5)", nativeQuery = true)
    void insertNewMajorLevel(Long fieldId, Long majorId, Long cvId, String level, boolean status);
}
