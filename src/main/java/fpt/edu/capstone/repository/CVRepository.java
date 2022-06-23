package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
public interface CVRepository extends JpaRepository<CV,Long> {

    @Query(value = "select * from cv c where c.candidate_id = ?1", nativeQuery = true)
    List<CV> findCvByCandidateId(Long candidateId);

    CV getByCandidateId(long candidateId);
    @Query(value = "SELECT * FROM public.cv WHERE id=?1", nativeQuery = true)
    Optional<CV> findCvById(long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO public.cv (candidate_id, is_deleted, summary, created_at, updated_at) VALUES( ?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
    void insertCv(Long candidateId, Long isDeleted, String summary, LocalDateTime createAt, LocalDateTime updateAt);
}