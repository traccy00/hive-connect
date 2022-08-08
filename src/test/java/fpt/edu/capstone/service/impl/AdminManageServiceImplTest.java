package fpt.edu.capstone.service.impl;

import static org.mockito.Mockito.when;

import fpt.edu.capstone.dto.admin.LicenseApprovalResponse;
import fpt.edu.capstone.dto.banner.ApproveBannerRequest;
import fpt.edu.capstone.dto.banner.UpdateBannerRequest;
import fpt.edu.capstone.dto.job.ReportJobRequest;
import fpt.edu.capstone.dto.job.ReportedJobResponse;
import fpt.edu.capstone.entity.*;
import fpt.edu.capstone.repository.ReportedRepository;
import fpt.edu.capstone.service.BannerActiveService;
import fpt.edu.capstone.service.JobService;
import fpt.edu.capstone.service.RecruiterService;

import fpt.edu.capstone.service.UserService;
import fpt.edu.capstone.utils.Enums;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
public class AdminManageServiceImplTest {
    @InjectMocks
    AdminManageServiceImpl adminManageService;

    @Mock
    RecruiterService recruiterService;

    @Mock
    UserService userService;

    @Mock
    JobService jobService;

    @Mock
    ReportedRepository reportedRepository;

    @Mock
    BannerActiveService bannerActiveService;

    @Test
    void searchLicenseApprovalForAdminTest(){
        String businessApprovalStatus = "";
        String additionalApprovalStatus = "";

        Recruiter recruiter = new Recruiter();
        recruiter.setUserId(123L);

        when(recruiterService.searchLicenseApprovalForAdmin(ArgumentMatchers.anyString(),ArgumentMatchers.anyString()))
                .thenReturn(Collections.singletonList(recruiter));

        List<LicenseApprovalResponse> responseList = adminManageService.searchLicenseApprovalForAdmin(businessApprovalStatus,additionalApprovalStatus);

        assertEquals(1,responseList.size());
//        assertEquals(123L,responseList.get(0).getRequestUserId());
    }

//    @Test
//    void reportJobTest() {
//        ReportJobRequest request = new ReportJobRequest();
//        request.setFullName("Lê Thị Tiểu Mai");
//        request.setJobId(1L);
//        request.setPhone("0379700425");
//        request.setUserAddress("Hà Nội");
//        request.setUserEmail("mai@gmail.com");
//        request.setReportReason("Bài viết không đúng sự thật.");
//
//        Users user = new Users();
//        user.setId(1L);
//        when(userService.getUserById(1L)).thenReturn(user);
//
//        Job job = new Job();
//        job.setId(1L);
//        when(jobService.getJobById(1L)).thenReturn(job);
//
//        Report report = jobService.reportJob(request, 1L);
//
//        Recruiter recruiter = new Recruiter();
//        when(recruiterService.getRecruiterById(1L)).thenReturn(recruiter);
//
//        assertEquals(reportedRepository.findById(1L), report);
//    }
//    @Test
//    void reportJobTest() {
//        ReportJobRequest reportJobRequest = new ReportJobRequest();
//        reportJobRequest.setFullName("Lê Thị Tiểu Mai");
//        reportJobRequest.setJobId(1L);
//        reportJobRequest.setPhone("0379700425");
//        reportJobRequest.setUserAddress("Hà Nội");
//        reportJobRequest.setUserEmail("mai@gmail.com");
//        reportJobRequest.setReportReason("Bài viết không đúng sự thật.");
//
//        Users user = new Users();
//        user.setId(1L);
//        when(userService.getUserById(1L)).thenReturn(user);
//
//        Job job = new Job();
//        job.setId(1L);
//        when(jobService.getJobById(1L)).thenReturn(job);
//
//        Report report = adminManageService.reportJob(reportJobRequest, 1L);
//
//        Recruiter recruiter = new Recruiter();
//        when(recruiterService.getRecruiterById(1L)).thenReturn(recruiter);
//
//        assertEquals(reportedRepository.findById(1L), report);
//    }

    @Test
    void searchReportedJobTest(){

    }

    @Test
    void approveBannerTest(){
        ApproveBannerRequest request = new ApproveBannerRequest();
        request.setBannerActiveId(1L);
        request.setApprovalStatus(Enums.ApprovalStatus.APPROVED.getStatus());

        BannerActive active = new BannerActive();
        active.setId(1L);
        when(bannerActiveService.findById(request.getBannerActiveId())).thenReturn(active);

    }

    @Test
    void getBannerOfRecruiterForAdminTest(){

    }

    @Test
    void searchUsersForAdminTest(){

    }

    @Test
    void searchReportedUsersTest(){

    }

    @Test
    void approveReportedJobTest(){

    }

}