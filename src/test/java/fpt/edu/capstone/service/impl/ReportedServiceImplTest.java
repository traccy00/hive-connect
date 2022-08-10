package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.admin.user.ReportedUserResponse;
import fpt.edu.capstone.dto.job.ReportJobRequest;
import fpt.edu.capstone.dto.job.ReportedJobResponse;
import fpt.edu.capstone.entity.Job;
import fpt.edu.capstone.entity.Recruiter;
import fpt.edu.capstone.entity.Report;
import fpt.edu.capstone.entity.Users;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.ReportedRepository;
import fpt.edu.capstone.service.JobService;
import fpt.edu.capstone.service.RecruiterService;
import fpt.edu.capstone.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportedServiceImplTest {

    @Mock
    private ReportedRepository mockReportedRepository;
    @Mock
    private UserService mockUserService;
    @Mock
    private JobService mockJobService;
    @Mock
    private ModelMapper mockModelMapper;
    @Mock
    private RecruiterService mockRecruiterService;

    private ReportedServiceImpl reportedServiceImplUnderTest;

    @Before
    public void setUp() {
        reportedServiceImplUnderTest = new ReportedServiceImpl(mockReportedRepository, mockUserService, mockJobService,
                mockModelMapper, mockRecruiterService);
    }

    private Users users(){
        Users users = new Users();
        users.setId(0L);
        users.setUsername("username");
        users.setPassword("password");
        users.setEmail("email");
        users.setPhone("0967445450");
        users.setRoleId(0L);
        users.setIsDeleted(0);
        users.setLastLoginTime(LocalDateTime.now());
        users.setVerifiedEmail(false);
        users.setVerifiedPhone(false);
        users.setActive(true);
        users.setLocked(false);
        users.setAvatar("avatar");
        users.setResetPasswordToken("setResetPasswordToken");
        users.setGoogle(false);
        return users;
    }

    private ReportJobRequest request(){
        ReportJobRequest request = new ReportJobRequest();
        request.setJobId(0L);
        request.setReportReason("reportReason");
        request.setRelatedLink("relatedLink");
        request.setFullName("fullName");
        request.setPhone("phone");
        request.setUserAddress("userAddress");
        request.setUserEmail("userEmail");
        return request;
    }

    private Job job(){
        Job job = new Job();
        job.setId(0L);
        job.setCompanyId(0L);
        job.setJobName("jobName");
        job.setWorkPlace("workPlace");
        job.setWorkForm("workForm");
        job.setStartDate(LocalDateTime.now());
        job.setEndDate(LocalDateTime.now().plusDays(10));
        job.setFromSalary(0L);
        job.setToSalary(0L);
        job.setNumberRecruits(0L);
        job.setRank("rank");
        job.setExperience("experience");
        job.setGender(false);
        job.setJobDescription("jobDescription");
        job.setJobRequirement("jobRequirement");
        job.setBenefit("benefit");
        job.setFieldId(0L);
        job.setIsDeleted(0);
        job.setPopularJob(false);
        job.setNewJob(false);
        job.setUrgentJob(false);
        job.setRecruiterId(0L);
        job.setWeekday("weekday");
        job.setCountryId(0L);
        job.setFlag("Posted");
        return job;
    }

    private Report report(){
        Report report = new Report();
        report.setId(0L);
        report.setReportReason("reportReason");
        report.setPersonReportId(0L);
        report.setPostId(0L);
        report.setRelatedLink("relatedLink");
        report.setApprovalReportedStatus("approvalReportedStatus");
        report.setFullName("fullName");
        report.setPhone("0967445450");
        report.setUserAddress("userAddress");
        report.setUserEmail("userEmail");
        report.setReportType("reportType");
        report.setJobId(0L);
        return report;
    }

    private Recruiter recruiter(){
        Recruiter recruiter = new Recruiter();
        recruiter.setId(0L);
        recruiter.setCompanyId(0L);
        recruiter.setCompanyName("companyName");
        recruiter.setFullName("fullName");
        recruiter.setVerifyAccount(true);
        recruiter.setGender(false);
        recruiter.setPosition("HR");
        recruiter.setLinkedinAccount("linkedinAccount");
        recruiter.setBusinessLicense("businessLicense");
        recruiter.setAdditionalLicense("additionalLicense");
        recruiter.setBusinessLicenseUrl("businessLicenseUrl");
        recruiter.setAdditionalLicenseUrl("additionalLicenseUrl");
        recruiter.setUserId(0L);
        recruiter.setDeleted(false);
        recruiter.setCompanyAddress("companyAddress");
        recruiter.setBusinessLicenseApprovalStatus("businessLicenseApprovalStatus");
        recruiter.setAvatarUrl("avatarUrl");
        recruiter.setTotalCvView(10);
        return recruiter;
    }

    @Test
    public void testSearchReportedUsers() {
        when(mockReportedRepository.searchReportedUsers(any(Pageable.class), eq("username"), eq("personReportName"),
                eq(Arrays.asList(0L)), eq(Arrays.asList(0L)))).thenReturn(new PageImpl<>(Arrays.asList()));
        final Page<ReportedUserResponse> result = reportedServiceImplUnderTest.searchReportedUsers(PageRequest.of(0, 1),
                "username", "personReportName", Arrays.asList(0L), Arrays.asList(0L));
    }

    @Test
    public void testSearchReportedUsers_ReportedRepositoryReturnsNoItems() {
        when(mockReportedRepository.searchReportedUsers(any(Pageable.class), eq("username"), eq("personReportName"),
                eq(Arrays.asList(0L)), eq(Arrays.asList(0L)))).thenReturn(new PageImpl<>(Collections.emptyList()));
        final Page<ReportedUserResponse> result = reportedServiceImplUnderTest.searchReportedUsers(PageRequest.of(0, 1),
                "username", "personReportName", Arrays.asList(0L), Arrays.asList(0L));
    }

    @Test
    public void testSearchReportedJob() {
        when(mockReportedRepository.searchReportedJob(any(Pageable.class), eq("jobName")))
                .thenReturn(new PageImpl<>(Arrays.asList()));
        final Page<ReportedJobResponse> result = reportedServiceImplUnderTest.searchReportedJob(PageRequest.of(0, 1),
                LocalDateTime.now(), LocalDateTime.now().plusDays(3),
                LocalDateTime.now(), LocalDateTime.now().plusDays(3), "jobName");
    }

    @Test
    public void testSearchReportedJob_ReportedRepositoryReturnsNoItems() {
        when(mockReportedRepository.searchReportedJob(any(Pageable.class), eq("jobName")))
                .thenReturn(new PageImpl<>(Collections.emptyList()));
        final Page<ReportedJobResponse> result = reportedServiceImplUnderTest.searchReportedJob(PageRequest.of(0, 1),
                LocalDateTime.now(), LocalDateTime.now().plusDays(3),
                LocalDateTime.now(), LocalDateTime.now().plusDays(3), "jobName");
    }

    @Test
    public void testReportJob() {
        final ReportJobRequest request = request();
        final Users users =users();
        when(mockUserService.getUserById(0L)).thenReturn(users);
        final Job job = job();
        when(mockJobService.getJobById(0L)).thenReturn(job);
        final Report report = report();
        when(mockModelMapper.map(any(Object.class), eq(Report.class))).thenReturn(report);
        final Recruiter recruiter = recruiter();
        when(mockRecruiterService.getRecruiterById(0L)).thenReturn(recruiter);
        final Report report1 = report();
        when(mockReportedRepository.save(any(Report.class))).thenReturn(report1);
        final Optional<Report> report2 = Optional.of(report());
        when(mockReportedRepository.findById(0L)).thenReturn(report2);
        final Report result = reportedServiceImplUnderTest.reportJob(request, 0L);
        verify(mockReportedRepository).save(any(Report.class));
    }

    @Test
    public void testReportJob_UserServiceReturnsNull() {
        final ReportJobRequest request = request();
        when(mockUserService.getUserById(0L)).thenReturn(null);
        assertThatThrownBy(() -> reportedServiceImplUnderTest.reportJob(request, 0L))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testReportJob_JobServiceReturnsNull() {
        final ReportJobRequest request = request();
        final Users users = users();
        when(mockUserService.getUserById(0L)).thenReturn(users);
        when(mockJobService.getJobById(0L)).thenReturn(null);
        assertThatThrownBy(() -> reportedServiceImplUnderTest.reportJob(request, 0L))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testReportJob_ReportedRepositoryFindByIdReturnsAbsent() {
        final ReportJobRequest request = request();
        final Users users = users();
        when(mockUserService.getUserById(0L)).thenReturn(users);
        final Job job = job();
        when(mockJobService.getJobById(0L)).thenReturn(job);
        final Report report = report();
        when(mockModelMapper.map(any(Object.class), eq(Report.class))).thenReturn(report);
        final Recruiter recruiter = recruiter();
        when(mockRecruiterService.getRecruiterById(0L)).thenReturn(recruiter);
        final Report report1 = report();
        when(mockReportedRepository.save(any(Report.class))).thenReturn(report1);
        when(mockReportedRepository.findById(0L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> reportedServiceImplUnderTest.reportJob(request, 0L))
                .isInstanceOf(HiveConnectException.class);
        verify(mockReportedRepository).save(any(Report.class));
    }
}
