package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.admin.user.ReportedUserResponse;
import fpt.edu.capstone.dto.job.ReportedJobResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportedService {

    Page<ReportedUserResponse> searchReportedUsers(Pageable pageable, String username, String personReportName,
                                                   List<Long> userId, List<Long> personReportId);
    void updateReportedStatus(String status, long id);

    Page<ReportedJobResponse> searchReportedJob(Pageable pageable, LocalDateTime createdAtFrom, LocalDateTime createAtTo,
                                                LocalDateTime updatedAtFrom, LocalDateTime updatedAtTo, String jobName);
}
