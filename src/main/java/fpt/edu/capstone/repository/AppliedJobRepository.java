package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.sprint1.AppliedJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppliedJobRepository extends JpaRepository<AppliedJob, Long> {

    boolean existsByCandidateIdAndJobId(long candidateId, long jobId);

    @Query(value = "select * from applied_job aj where aj.candidate_id = ?1 and job_id = ?2 order by updated_at desc limit 1", nativeQuery = true)
    AppliedJob findByCandidateIdAndJobId(long candidateId, long jobId);

    @Query(value = "select * from applied_job aj where aj.job_id = ?1 and aj.is_applied = ?2", nativeQuery = true)
    List<AppliedJob> getListCandidateAppliedJob(long jobId, boolean isApplied);
}
