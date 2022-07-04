package fpt.edu.capstone.dto.admin.user;

import java.time.LocalDateTime;

public interface ReportedUserResponse {
    long getReportedId();
    long getUserId();
    String getUserName();
    String getReportedReason();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
    long getPersonReport();
    String getPersonReportName();
    String getRelatedLink();
    String getApprovalReportedStatus();
}
