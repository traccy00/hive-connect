package fpt.edu.capstone.repository;

import fpt.edu.capstone.dto.company.CompanyResponse;
import fpt.edu.capstone.entity.AppliedJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppliedJobRepository extends JpaRepository<AppliedJob, Long> {

    boolean existsByCandidateIdAndJobId(long candidateId, long jobId);

    @Query(value = "select * from applied_job aj where aj.candidate_id = ?1 and job_id = ?2 " +
            "order by updated_at desc limit 1", nativeQuery = true)
    AppliedJob getByCandidateIdAndJobId(long candidateId, long jobId);

    @Query(value = "select * from applied_job aj where aj.job_id = ?1 and aj.is_applied = ?2", nativeQuery = true)
    Page<AppliedJob> getCvAppliedJob(Pageable pageable, long jobId, boolean isApplied);

    @Query(value = "select * from applied_job aj where job_id = ?1 and candidate_id = ?2 " +
            "and approval_status like concat('%', ?3, '%') and is_applied = ?4 " +
            "order by created_at desc limit 1;", nativeQuery = true)
    AppliedJob getAppliedJobPendingApproval(long jobId, long candidateId, String approvalStatus, boolean isApplied);

    @Query(value = "select count(job_id) as applyCvNumber, t1.company_id as companyId, t1.company_name as companyName from " +
            "(select aj.job_id, j.company_id, c.name as company_name from applied_job aj " +
            "join job j on aj.job_id = j.id " +
            "join companies c on j.company_id = c.id) t1 " +
            "group by company_id, t1.company_name order by applyCvNumber desc limit 12;", nativeQuery = true)
    List<CompanyResponse> getTop12Companies();
}
