package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.CV;
import fpt.edu.capstone.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, CV> {
    @Query(value = "select * from Language l where l.cv_id = ?1", nativeQuery = true)
    List<Language> getListLanguageByCvId(Long cvId);
}
