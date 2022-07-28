package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.admin.LicenseApprovalResponse;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.job.ReportJobRequest;
import fpt.edu.capstone.dto.job.ReportedJobResponse;
import fpt.edu.capstone.entity.Job;
import fpt.edu.capstone.entity.Recruiter;
import fpt.edu.capstone.entity.Report;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.ReportedRepository;
import fpt.edu.capstone.service.*;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseDataPagination;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminManageServiceImpl implements AdminManageService {

    private final RecruiterService recruiterService;

    private final ModelMapper modelMapper;

    private final ReportedRepository reportedRepository;

    private final UserService userService;

    private final JobService jobService;

    private final ReportedService reportedService;

    @Override
    public List<LicenseApprovalResponse> searchLicenseApprovalForAdmin(String businessApprovalStatus, String additionalApprovalStatus) {
        List<LicenseApprovalResponse> responseList = new ArrayList<>();
        List<Recruiter> recruiters = recruiterService.searchLicenseApprovalForAdmin(businessApprovalStatus, additionalApprovalStatus);
        for (Recruiter recruiter : recruiters) {
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

    @Override
    public Report reportJob(ReportJobRequest request, long userId) {
        if (userService.getUserById(userId) == null) {
            throw new HiveConnectException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
        }
        if (jobService.getJobById(request.getJobId()) == null) {
            throw new HiveConnectException("Tin tuyển dụng không tồn tại");
        }
        boolean a = request.getFullName().trim().isEmpty();
        if ((request.getFullName() == null || request.getFullName().trim().isEmpty())
                || (request.getPhone() == null || request.getPhone().trim().isEmpty())
                || (request.getUserAddress() == null || request.getUserAddress().trim().isEmpty())
                || (request.getUserEmail() == null || request.getUserEmail().trim().isEmpty())
                || (request.getReportReason() == null || request.getReportReason().trim().isEmpty())) {
            throw new HiveConnectException("Vui lòng điền các thông tin bắt buộc");
        }
        Report report = modelMapper.map(request, Report.class);
        Job job = jobService.getJobById(request.getJobId());
        Recruiter recruiter = recruiterService.getRecruiterById(job.getRecruiterId());
        report.setPersonReportId(userId);
        report.setReportedUserId(recruiter.getUserId());
        report.create();
        report.setApprovalReportedStatus(Enums.ApprovalStatus.PENDING.getStatus());
        report.setUpdatedAt(null);
        report.setReportType("1");
        reportedRepository.save(report);
        if (!reportedRepository.findById(report.getId()).isPresent()) {
            throw new HiveConnectException("Báo cáo thất bại.");
        }
        return report;
    }

    @Override
    public ResponseDataPagination searchReportedJob(Integer pageNo, Integer pageSize, LocalDateTime createdAtFrom,
                                                    LocalDateTime createAtTo, LocalDateTime updatedAtFrom,
                                                    LocalDateTime updatedAtTo, String jobName) {
        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);
        Page<ReportedJobResponse> reportedJobResponse = reportedService.
                searchReportedJob(pageable, createdAtFrom, createAtTo, updatedAtFrom, updatedAtTo, jobName);
        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();
        responseDataPagination.setData(reportedJobResponse.getContent());
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(reportedJobResponse.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(reportedJobResponse.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);
        return responseDataPagination;
    }
}
