package fpt.edu.capstone.repository;

import fpt.edu.capstone.dto.AppliedJobByRecruiterResponse;
import fpt.edu.capstone.entity.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {

    @Query(value = "select * from recruiter r where r.user_id = ?", nativeQuery = true)
    Recruiter getRecruiterProfileByUserId(long userId);

    Optional<Recruiter> findByUserId(long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE public.recruiter SET fullname=?1, gender=?2, position=?3, linkedin_url=?4, business_license=?5, updated_at=?6, phone_number=?7, additional_license=?8, avatar_url = ?9 where id = ?10", nativeQuery = true)
    void updateCruiterProfile(String fullName, boolean gender, String position, String linkedinUrl, String businessLience, LocalDateTime updateAt, String phoneNumber, String additionalLience, String avatarUrl, long id);

    @Query(value = "select j.job_name as jobName, aj.id as id, aj.job_id as jobId, aj.candidate_id as candidateId, aj.is_applied as isApplied, aj.approval_status as approvalStatus, aj.is_upload_cv  as isUploadCv, aj.cv_upload_url as cvUploadUrl, r.id  as recruiterId  from job j join applied_job aj on j.id = aj.job_id join recruiter r on j.recruiter_id = r.id where r.id = ?1", nativeQuery = true)
    List<AppliedJobByRecruiterResponse> getListAppliedByForRecruiter(long recruiterId);
}
