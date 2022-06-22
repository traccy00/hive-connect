package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CVReposiroty extends JpaRepository<CV,Long> {

    @Query(value = "select * from cv c where c.candidate_id = ?1", nativeQuery = true)
    CV findCvByCandidateId(Long candidateId);



}
