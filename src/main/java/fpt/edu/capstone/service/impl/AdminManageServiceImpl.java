package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.admin.LicenseApprovalResponse;
import fpt.edu.capstone.entity.Recruiter;
import fpt.edu.capstone.service.AdminManageService;
import fpt.edu.capstone.service.RecruiterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminManageServiceImpl implements AdminManageService {

    private final RecruiterService recruiterService;

    @Override
    public List<LicenseApprovalResponse> searchLicenseApprovalForAdmin(String businessApprovalStatus, String additionalApprovalStatus) {
        List<LicenseApprovalResponse> responseList = new ArrayList<>();
        List<Recruiter> recruiters = recruiterService.searchLicenseApprovalForAdmin(businessApprovalStatus, additionalApprovalStatus);
        for(Recruiter recruiter : recruiters) {
            LicenseApprovalResponse response = new LicenseApprovalResponse();
            response.setRequestUserId(recruiter.getUserId());
            response.setRequestUserName(recruiter.getFullName());
            response.setBusinessLicenseUrl(recruiter.getBusinessLicenseUrl());
            response.setBusinessLicenseApprovalStatus(recruiter.getBusinessLicenseApprovalStatus());
            response.setAdditionalLicenseUrl(recruiter.getAdditionalLicenseUrl());
            response.setAdditionalLicenseApprovalStatus(recruiter.getAdditionalLicenseApprovalStatus());
            response.setCreatedAt(recruiter.getCreatedAt());
            response.setUpdatedAt(recruiter.getUpdatedAt());
            responseList.add(response);
        }
        return responseList;
    }
}
