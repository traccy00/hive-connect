package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.sprint1.AppliedJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppliedJobRepository extends JpaRepository<AppliedJob, Long> {
    boolean existsByCandidateId(long candidateId);

    @Query(value = "select * from applied_job aj where job_id = ?1", nativeQuery = true)
    List<AppliedJob> getListCandidateAppliedJob(long jobId);

}
