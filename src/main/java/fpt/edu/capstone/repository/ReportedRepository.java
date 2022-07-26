package fpt.edu.capstone.repository;

import fpt.edu.capstone.dto.admin.user.ReportedUserResponse;
import fpt.edu.capstone.dto.job.ReportedJobResponse;
import fpt.edu.capstone.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ReportedRepository extends JpaRepository<Report, Long> {
    @Query(value = "select r.id as reportedId, r.reported_user_id as reportedUserId, u.username as userName, r.report_reason as reportReason, " +
            "r.created_at as createdAt, r.updated_at as updatedAt, r.person_report_id as personReport, u2.username as personReportName, " +
            "r.related_link as relatedLink, r.approval_reported_status as approvalReportedStatus " +
            "from reported r join users u on r.reported_user_id = u.id " +
            "join users u2 on r.person_report_id = u2.id " +
            "where lower(u.username) like lower(concat('%',:username,'%')) " +
            "and lower(u2.username) like lower(concat('%',:personReportName,'%')) " +
            "and u.id in (:userId) and u2.id in (:personReportId) and r.report_type = '2' order by r.created_at desc", nativeQuery = true)
    Page<ReportedUserResponse> searchReportedUsers(Pageable pageable, String username, String personReportName,
                                                   List<Long> userId, List<Long> personReportId);

    @Modifying
    @Transactional
    @Query(value = "Update reported set approval_reported_status = ?1 where id = ?2", nativeQuery = true)
    void updateReportedStatus(String status, long id);

    @Query(value = "select j.job_name as jobName, c.name as companyName, r2.user_id as reportedUserId, r.fullname as fullName, " +
            "r.phone as phone, r.user_address as userAddress, r.user_email as userEmail, " +
            "r.created_at as createdAt, r.person_report_id as personReportId, r.updated_at as updatedAt, r.approval_reported_status as approvalReportedStatus " +
            "from reported r " +
            "join job j on j.id = r.job_id " +
            "join companies c on j.company_id = c.id " +
            "join recruiter r2 on j.recruiter_id = r2.id " +
            "where r.report_type = '1' " +
            "and j.job_name like concat('%',:jobName,'%')", nativeQuery = true)
    Page<ReportedJobResponse> searchReportedJob(Pageable pageable, String jobName);
}
