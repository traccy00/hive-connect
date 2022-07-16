package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.admin.LicenseApprovalResponse;
import fpt.edu.capstone.entity.Recruiter;

import java.util.List;

public interface AdminManageService {
    List<LicenseApprovalResponse> searchLicenseApprovalForAdmin(String businessApprovalStatus,
                                                                String additionalApprovalStatus);
}
