package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.admin.LicenseApprovalResponse;
import fpt.edu.capstone.dto.job.ReportJobRequest;
import fpt.edu.capstone.entity.Job;
import fpt.edu.capstone.entity.Recruiter;
import fpt.edu.capstone.entity.Report;
import fpt.edu.capstone.entity.Users;
import fpt.edu.capstone.repository.ReportedRepository;
import fpt.edu.capstone.service.*;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.ResponseDataPagination;
import io.swagger.v3.oas.models.info.License;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.mockito.Mockito.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//@ActiveProfiles(profiles = { "db2-local", "ia-off"})
//@WebAppConfiguration
//@ContextConfiguration(locations = { "classpath:spring/*.xml" })

//@NoArgsConstructor
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

//    @BeforeClass
//    public static void setUpBeforeClass() throws Exception {
//        System.out.println("before class");
//    }
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//    }

    @Test
    public void searchLicenseApprovalForAdmin() {
//        String businessApprovalStatus = Enums.ApprovalStatus.APPROVED.getStatus();
//        String additionalApprovalStatus = Enums.ApprovalStatus.APPROVED.getStatus();
//        List<LicenseApprovalResponse> responseList = new ArrayList<>();
//        List<Recruiter> recruiters = new ArrayList<>();
//        LicenseApprovalResponse response = new LicenseApprovalResponse();
//        Recruiter recruiter = new Recruiter(1, 1, "FPT Software", "Mai Le",
//                true, true, "HR", null, businessApprovalStatus,
//                additionalApprovalStatus, null, null, 1, false,
//                "Hà Nội", null, null, null);
//        recruiters.add(recruiter);
//        when(recruiterService
//                .searchLicenseApprovalForAdmin(businessApprovalStatus, additionalApprovalStatus))
//                .thenReturn(recruiters);
//        responseList.add(response);
//        when(adminManageService
//                .searchLicenseApprovalForAdmin(businessApprovalStatus, additionalApprovalStatus))
//                .thenReturn(responseList);
    }

    @Test
    public void reportJob() {
        Users user = new Users();
        user.setId(1);
        user.setUsername("mai");
        Report report = new Report();
        when(adminManageService.reportJob(Mockito.any(ReportJobRequest.class), 1)).thenReturn(report);
        when(userService.getUserById(6)).thenReturn(new Users());
//        Job job = new Job();
//        when(jobService.getJobById(1)).thenReturn(job);
    }
}
