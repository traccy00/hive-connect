package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.admin.LicenseApprovalResponse;
import fpt.edu.capstone.entity.Recruiter;
import fpt.edu.capstone.service.RecruiterService;
import io.swagger.v3.oas.models.info.License;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.mockito.Mockito.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ActiveProfiles(profiles = { "db2-local", "ia-off"})
//@WebAppConfiguration
//@ContextConfiguration(locations = { "classpath:spring/*.xml" })

//@Transactional
public class AdminManageServiceImplTest {

    @InjectMocks
    private AdminManageServiceImpl adminManageService;

    @Mock
    private RecruiterService recruiterService;

    @Before
    public void setup() {
        //Set Mock
        MockitoAnnotations.initMocks(this);
        //Set Session
//        String sessionId =
    }

    @Test
    @Rollback(true)
    public void searchLicenseApprovalForAdmin() {
        String businessApprovalStatus = "";
        String additionalApprovalStatus = "";
        List<LicenseApprovalResponse> responseList = new ArrayList<>();
        List<Recruiter> recruiters = new ArrayList<>();
        when(recruiterService.searchLicenseApprovalForAdmin(businessApprovalStatus, additionalApprovalStatus)).thenReturn(recruiters);
    }

}
