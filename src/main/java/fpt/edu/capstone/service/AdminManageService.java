package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.admin.LicenseApprovalResponse;
import fpt.edu.capstone.dto.job.ReportJobRequest;
import fpt.edu.capstone.entity.Report;
import fpt.edu.capstone.utils.ResponseDataPagination;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminManageService {
    List<LicenseApprovalResponse> searchLicenseApprovalForAdmin(String businessApprovalStatus,
                                                                String additionalApprovalStatus);

    Report reportJob(ReportJobRequest request, long userId);

    ResponseDataPagination searchReportedJob(Integer pageNo, Integer pageSize, LocalDateTime createdAtFrom,
                                             LocalDateTime createAtTo, LocalDateTime updatedAtFrom,
                                             LocalDateTime updatedAtTo, String jobName);

    void approveBanner(long bannerActiveId);

    ResponseDataPagination getBannerOfRecruiterForAdmin(Integer pageNo, Integer pageSize);
}
