package fpt.edu.capstone.repository;

import fpt.edu.capstone.dto.AppliedJobByRecruiterResponse;
import fpt.edu.capstone.dto.admin.user.RecruiterManageResponse;
import fpt.edu.capstone.entity.Recruiter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Modifying
    @Transactional
    @Query(value = "UPDATE public.recruiter SET avatar_url=?1 WHERE id=?2", nativeQuery = true)
    void updateAvatar(String img, long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE public.recruiter SET company_id=?1 WHERE id=?2", nativeQuery = true)
    void updateCompany(long companyId, long id);

    @Query(value = "select u.id as userId, u.username as userName, u.email as email, u.role_id as roleId, r2.name as roleName, u.is_deleted as isDeleted, u.last_login_time as lastLoginTime, " +
            "u.avatar as avatar, u.is_verified_email as isVerifiedEmail, u.is_active as isActive, r.id as recruiterId, r.company_id as companyId, c.name as companyName, " +
            "r.fullname as fullName, r.verify_account as verifyAccount, r.verify_phonenumber as verifyPhoneNumber, r.gender as gender, r.position as position, " +
            "r.linkedin_url as linkedInAccount, r.business_license as businessLicense, r.additional_license as additionalLicense, r.phone_number as phoneNumber, " +
            "r.created_at as createdAt, r.updated_at as updatedAt, r.company_address as companyAddress " +
            "from users u join recruiter r on u.id = r.user_id " +
            "join roles r2 on u.role_id = r2.id " +
            "join companies c on r.company_id = c.id " +
            "where u.username like concat('%',:username,'%') and u.email like concat('%',:email,'%')", nativeQuery = true)
    Page<RecruiterManageResponse> searchRecruitersForAdmin(Pageable pageable, @Param("username") String username, @Param("email") String email);

    Optional<Recruiter> findByPhoneNumber(String phone);
}
