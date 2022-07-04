package fpt.edu.capstone.repository;

import fpt.edu.capstone.dto.admin.user.ReportedUserResponse;
import fpt.edu.capstone.entity.Reported;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportedRepository extends JpaRepository<Reported, Long> {
    @Query(value = "select r.id as reportedId, r.user_id as userId, u.username as userName, r.reported_reason as reportedReason, " +
            "r.created_at as createdAt, r.updated_at as updatedAt, r.person_report as personReport, u2.username as personReportName, " +
            "r.related_link as relatedLink, r.approval_reported_status as approvalReportedStatus " +
            "from reported r join users u on r.user_id = u.id " +
            "join users u2 on r.person_report = u2.id " +
            "where lower(u.username) like lower(concat('%',:username,'%')) " +
            "and lower(u2.username) like lower(concat('%',:personReportName,'%')) " +
            "and u.id in (:userId) and u2.id in (:personReportId)", nativeQuery = true)
    Page<ReportedUserResponse> searchReportedUsers(Pageable pageable, String username, String personReportName,
                                                   List<Long> userId, List<Long> personReportId);
}
