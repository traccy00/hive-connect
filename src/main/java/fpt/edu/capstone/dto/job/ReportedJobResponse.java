package fpt.edu.capstone.dto.job;

import java.time.LocalDateTime;

public interface ReportedJobResponse {
    String getJobName();
    String getCompanyName();
    long getReportedUserId();
    String getFullName();
    String getPhone();
    String getUserAddress();
    String getUserEmail();
    LocalDateTime getCreatedAt();
    long getPersonReportId();
    LocalDateTime getUpdatedAt();
    String getApprovalReportedStatus();
}
