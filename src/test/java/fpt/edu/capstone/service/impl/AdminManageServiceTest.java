package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.admin.LicenseApprovalResponse;
import fpt.edu.capstone.dto.job.ReportJobRequest;
import fpt.edu.capstone.dto.job.ReportedJobResponse;
import fpt.edu.capstone.entity.Job;
import fpt.edu.capstone.entity.Recruiter;
import fpt.edu.capstone.entity.Report;
import fpt.edu.capstone.entity.Users;
import fpt.edu.capstone.repository.ReportedRepository;
import fpt.edu.capstone.service.JobService;
import fpt.edu.capstone.service.RecruiterService;
import fpt.edu.capstone.service.ReportedService;
import fpt.edu.capstone.service.UserService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseDataPagination;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminManageServiceTest {

    @InjectMocks
    AdminManageServiceImpl adminManageService;

    @Mock
    private RecruiterService recruiterService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ReportedRepository reportedRepository;

    @Mock
    private UserService userService;

    @Mock
    private JobService jobService;

    @Mock
    private ReportedService reportedService;

    @Mock
    private Page<ReportedJobResponse> reportedJobResponsePage;

    @Mock
    private Pageable pageable;



    @Test
    public void searchLicenseApprovalForAdmin() {
        String businessApprovalStatus = Enums.ApprovalStatus.APPROVED.getStatus();
        String additionalApprovalStatus = Enums.ApprovalStatus.APPROVED.getStatus();
        List<LicenseApprovalResponse> responseList = new ArrayList<>();
        List<Recruiter> recruiters = new ArrayList<>();
        LicenseApprovalResponse response = new LicenseApprovalResponse();
        Recruiter recruiter = new Recruiter();
        recruiter.setUserId(1);
        recruiter.setCompanyId(1);
        recruiter.setCompanyName("FPT Software");
        recruiter.setFullName("Lê Thị Tiểu Mai");
        recruiter.setPosition("HR");
        recruiter.setUserId(1);
        recruiter.setDeleted(false);
        recruiters.add(recruiter);
        when(recruiterService
                .searchLicenseApprovalForAdmin(businessApprovalStatus, additionalApprovalStatus))
                .thenReturn(recruiters);
        responseList.add(response);
//        when(adminManageService
//                .searchLicenseApprovalForAdmin(businessApprovalStatus, additionalApprovalStatus))
//                .thenReturn(responseList);
//        adminManageService.searchLicenseApprovalForAdmin()
    }

    @Test
    //(expected = Exception.class)
    public void reportJob() {
        ReportJobRequest request = new ReportJobRequest();
        long userId = 1;

//        request.setFullName("Lê Thị Tiểu Mai");
//        request.setPhone("0379700427");
//        request.setUserAddress("Hà Nội");
//        request.setReportReason("Tin tuyển dụng có thông tin không chính xác.");
//        request.setJobId(2);
//
        Users user = new Users();
        user.setId(1);
        user.setUsername("mai");
//        when(userService.getUserById(anyInt())).thenReturn(user);
//        userService.getUserById()
//
//        Job job = new Job();
//        job.setId(2);
//        when(jobService.getJobById(2)).thenReturn(job);
//
//        Report report = new Report();
//
//        when(adminManageService.reportJob(Mockito.any(ReportJobRequest.class), 1)).thenReturn(report);

        adminManageService.reportJob(request, userId);

    }

    @Test
    public void searchReportedJob() {
        int pageNo = 0;
        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
//        Pageable pageable = PageRequest.of(0, 10);
//        when(PageRequest.of(anyInt(), anyInt())).thenReturn(PageRequest.of(0, 10));
//        when(reportedService.searchReportedJob(eq(pageable), any(LocalDateTime.class), any(LocalDateTime.class), any(LocalDateTime.class), any(LocalDateTime.class), anyString()))
//                .thenReturn(reportedJobResponsePage);
//        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
//        Pagination pagination = new Pagination();
//
//        when(adminManageService.searchReportedJob(anyInt(), anyInt(), any(LocalDateTime.class), any(LocalDateTime.class), any(LocalDateTime.class), any(LocalDateTime.class), anyString()))
//                .thenReturn(responseDataPagination);
    }
}
