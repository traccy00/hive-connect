package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.admin.LicenseApprovalResponse;
import fpt.edu.capstone.dto.admin.user.ReportedUserResponse;
import fpt.edu.capstone.dto.banner.ApproveBannerRequest;
import fpt.edu.capstone.dto.banner.BannerForApprovalResponse;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.job.ReportJobRequest;
import fpt.edu.capstone.dto.job.ReportedJobResponse;
import fpt.edu.capstone.entity.*;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.BannerActiveRepository;
import fpt.edu.capstone.repository.JobRepository;
import fpt.edu.capstone.repository.ReportedRepository;
import fpt.edu.capstone.service.*;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseData;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminManageServiceImpl implements AdminManageService {

    private final RecruiterService recruiterService;

    private final ModelMapper modelMapper;

    private final ReportedRepository reportedRepository;

    private final UserService userService;

    private final JobService jobService;

    private final ReportedService reportedService;

    private final BannerActiveService bannerActiveService;

    private final BannerService bannerService;

    private final PaymentService paymentService;

    private final CompanyService companyService;

    private final BannerActiveRepository bannerActiveRepository;

    private final CandidateService candidateService;

    private final AdminService adminService;

    private final JobRepository jobRepository;

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
        if ((request.getFullName() == null || request.getFullName().trim().isEmpty())
                || (request.getPhone() == null || request.getPhone().trim().isEmpty())
                || (request.getUserAddress() == null || request.getUserAddress().trim().isEmpty())
                || (request.getUserEmail() == null || request.getUserEmail().trim().isEmpty())
                || (request.getReportReason() == null || request.getReportReason().trim().isEmpty())) {
            throw new HiveConnectException(ResponseMessageConstants.REQUIRE_INPUT_MANDATORY_FIELD);
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

    @Override
    public void approveBanner(ApproveBannerRequest request) {
        BannerActive bannerActive = bannerActiveService.findById(request.getBannerActiveId());
        if (bannerActive.getApprovalStatus().equals(Enums.ApprovalStatus.PENDING.getStatus())) {
            if (!request.getApprovalStatus().equals(Enums.ApprovalStatus.APPROVED.getStatus())
                    && !request.getApprovalStatus().equals(Enums.ApprovalStatus.REJECT.getStatus())) {
                throw new HiveConnectException(ResponseMessageConstants.APPROVAL_STATUS_INVALID);
            }
            bannerActive.setApprovalStatus(request.getApprovalStatus());
            bannerActiveRepository.save(bannerActive);
        } else {
            throw new HiveConnectException(ResponseMessageConstants.APPROVAL_WAS_PROCESSED);
        }
    }

    @Override
    public ResponseDataPagination getBannerOfRecruiterForAdmin(Integer pageNo, Integer pageSize) {
        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);

        Page<BannerActive> bannerActives = bannerActiveService.getAllBannerForApproval(pageable);
        List<BannerForApprovalResponse> responseList = new ArrayList<>();
        if (bannerActives.hasContent()) {
            for (BannerActive bannerActive : bannerActives) {
                Payment payment = paymentService.findById(bannerActive.getPaymentId());
                BannerForApprovalResponse response = new BannerForApprovalResponse();
                response.setBannerActiveId(bannerActive.getId());
                response.setDisplayPosition(bannerActive.getDisplayPosition());
                Banner banner = bannerService.findById(payment.getBannerId());
                response.setPackageName(banner.getTitle());
                Optional<Recruiter> recruiter = recruiterService.findById(payment.getRecruiterId());
                if (!recruiter.isPresent()) {
                    throw new HiveConnectException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
                }
                Optional<Company> company = companyService.findById(recruiter.get().getCompanyId());
                response.setRecruiterName(recruiter.get().getFullName());
                if (company.isPresent()) {
                    response.setCompanyId(recruiter.get().getCompanyId());
                    response.setCompanyName(company.get().getName());
                }
                response.setApplyStartDate(payment.getCreatedAt());
                response.setApplyEndDate(payment.getExpiredDate());
                response.setBuyDate(payment.getCreatedAt());
                response.setApprovalDate(bannerActive.getApprovalDate());
                response.setApprovalStatus(bannerActive.getApprovalStatus());
                responseList.add(response);
            }
        }
        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();
        responseDataPagination.setData(responseList);
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(bannerActives.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(bannerActives.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);
        return responseDataPagination;
    }

    @Override
    public ResponseDataPagination searchUsersForAdmin(String selectTab, Integer pageNo, Integer pageSize, String username, String email) {
        ResponseDataPagination responseDataPagination = new ResponseDataPagination();

        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);
        Page users;
        if (selectTab.equals("Recruiter")) {
            users = recruiterService.searchRecruitersForAdmin(pageable, username, email);
        } else if (selectTab.equals("Candidate")) {
            users = candidateService.searchCandidatesForAdmin(pageable, username, email);
        } else if (selectTab.equals("Admin")) {
            users = adminService.searchAdmins(pageable, username, email);
        } else {
            throw new HiveConnectException("Vui lòng chọn danh sách loại người dùng.");
        }

        responseDataPagination.setData(users);

        Pagination pagination = new Pagination();
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(users.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(users.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);

        return responseDataPagination;
    }

    @Override
    public ResponseDataPagination searchReportedUsers(Integer pageNo, Integer pageSize, String username,
                                                      String personReportName, List<Long> userIdList, List<Long> personReportId) {
        ResponseDataPagination responseDataPagination = new ResponseDataPagination();

        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);
        List<Users> users = userService.findAll();
        List<Long> userIdListIfEmpty = users.stream().map(Users::getId).collect(Collectors.toList());
        if (userIdList == null) {
            userIdList = userIdListIfEmpty;
        }
        if (personReportId == null) {
            personReportId = userIdListIfEmpty;
        }
        Page<ReportedUserResponse> reportedUsers = reportedService.searchReportedUsers(pageable, username,
                personReportName, userIdList, personReportId);

        responseDataPagination.setData(reportedUsers.getContent());
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(reportedUsers.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(reportedUsers.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);

        return responseDataPagination;
    }

    public String approveReportedJob(String approvalStatus, long reportId) {
        if (approvalStatus != null && (!approvalStatus.equals(Enums.ApprovalStatus.APPROVED.getStatus())
                && !approvalStatus.equals(Enums.ApprovalStatus.REJECT.getStatus()))) {
            throw new HiveConnectException(ResponseMessageConstants.APPROVAL_STATUS_INVALID);
        }
        Optional<Report> report = reportedRepository.findById(reportId);
        if (!report.isPresent()) {
            throw new HiveConnectException(ResponseMessageConstants.REPORTED_JOB_DOES_NOT_EXIST);
        }
        if (!report.get().getApprovalReportedStatus().equals(Enums.ApprovalStatus.PENDING.getStatus())) {
            throw new HiveConnectException(ResponseMessageConstants.APPROVAL_WAS_PROCESSED);
        }
        Job job = jobService.getJobById(report.get().getJobId());
        if (job == null) {
            throw new HiveConnectException(ResponseMessageConstants.JOB_DOES_NOT_EXIST);
        }
        if (approvalStatus.equals(Enums.ApprovalStatus.APPROVED.getStatus())) {
            job.setIsDeleted(1);
            job.update();
            jobRepository.save(job);
//            jobService.updateIsDeleted(1, job.get().getId());
            report.get().setApprovalReportedStatus(approvalStatus);
            report.get().update();
            reportedRepository.save(report.get());
//            reportedService.updateReportedStatus(approvalStatus, reportId);
            return ResponseMessageConstants.DELETE_SUCCESSFULLY;
        } else if (approvalStatus.equals(Enums.ApprovalStatus.REJECT.getStatus())) {
            report.get().setApprovalReportedStatus(approvalStatus);
            report.get().update();
            reportedRepository.save(report.get());
//            reportedService.updateReportedStatus(approvalStatus, reportId);
            return ResponseMessageConstants.CANCEL_REPORT_JOB_SUCCESSFULLY;
        }
        return null;
    }
}
