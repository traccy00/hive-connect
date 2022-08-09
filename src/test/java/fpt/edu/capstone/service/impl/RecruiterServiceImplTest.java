package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.common.user.GooglePojo;
import fpt.edu.capstone.dto.AppliedJobByRecruiterResponse;
import fpt.edu.capstone.entity.Recruiter;
import fpt.edu.capstone.entity.Users;
import fpt.edu.capstone.repository.RecruiterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
public class RecruiterServiceImplTest {
    @InjectMocks
    RecruiterServiceImpl recruiterService;

    @Mock
    RecruiterRepository recruiterRepository;

    @Test
    void getRecruiterByUserIdTest(){
        Recruiter recruiter = new Recruiter();
        recruiter.setId(1L);
        recruiter.setUserId(123L);
        recruiter.setFullName("tuyendung");
        Mockito.when(recruiterRepository.getRecruiterByUserId(ArgumentMatchers.anyLong())).thenReturn(recruiter);
        Recruiter r = recruiterService.getRecruiterByUserId(1L);
        assertEquals(1L, recruiter.getId());
    }

    @Test
    void existByIdTest(){
        Recruiter recruiter = new Recruiter();
        recruiter.setId(1L);
        recruiter.setUserId(123L);
        recruiter.setFullName("tuyendung");
        Mockito.when(recruiterRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
        assertEquals(true, recruiterService.existById(1L));
    }

    @Test
    void getRecruiterByIdTest(){
        Recruiter recruiter = new Recruiter();
        recruiter.setId(1L);
        recruiter.setUserId(123L);
        recruiter.setFullName("tuyendung");
        Mockito.when(recruiterRepository.getById(ArgumentMatchers.anyLong())).thenReturn(recruiter);
        Recruiter r = recruiterService.getRecruiterById(1L);
        assertEquals(1L, recruiter.getId());
    }

    @Test
    void findRecruiterByUserIdTest(){
        Recruiter recruiter = new Recruiter();
        recruiter.setId(1L);
        recruiter.setUserId(123L);
        recruiter.setFullName("tuyendung");
        Mockito.when(recruiterRepository.findByUserId(ArgumentMatchers.anyLong())).thenReturn(Optional.of(recruiter));
        Optional <Recruiter> rec = recruiterService.findRecruiterByUserId(123L);
        assertEquals(123L, rec.get().getUserId());
    }

    @Test
    void findByIdTest(){
        Recruiter recruiter = new Recruiter();
        recruiter.setId(1L);
        recruiter.setUserId(123L);
        recruiter.setFullName("tuyendung");
        Mockito.when(recruiterRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(recruiter));
        Optional <Recruiter> rec = recruiterService.findById(123L);
        assertEquals(123L, rec.get().getUserId());
    }

    @Test
    void insertRecruiterTest(){
        Recruiter recruiter = new Recruiter();
        recruiter.setId(1L);
        recruiter.setUserId(123L);
        recruiter.setFullName("tuyendung");
        Mockito.when(recruiterRepository.save(ArgumentMatchers.any(Recruiter.class))).thenReturn(recruiter);
        Recruiter rec = recruiterService.insertRecruiter(123L);
        assertEquals(1L, rec.getId());
    }

    @Test
    void getListAppliedByForRecruiterTest(){
        Recruiter recruiter = new Recruiter();
        recruiter.setId(1L);
        recruiter.setUserId(123L);
        recruiter.setFullName("tuyendung");
        Mockito.when(recruiterRepository.getListAppliedByForRecruiter(ArgumentMatchers.anyLong())).thenReturn(null);
        List<AppliedJobByRecruiterResponse> result = recruiterService.getListAppliedByForRecruiter(recruiter.getId());
//        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void updateCompanyTest(){
        Recruiter recruiter = new Recruiter();
        recruiter.setId(1L);
        recruiter.setUserId(123L);
        recruiter.setFullName("tuyendung");
        recruiter.setCompanyId(1234L);
        recruiterService.updateCompany(recruiter.getCompanyId(), recruiter.getId());
    }

    @Test
    void getRecruiterByCompanyIdTest(){
//        Page<Recruiter> recruiterPage = null;
//        Pageable pageable = PageRequest.of(0,10);
//        Recruiter recruiter = new Recruiter();
//        recruiter.setId(1L);
//        recruiter.setUserId(123L);
//        recruiter.setCompanyId(1234L);
//        Mockito.when(recruiterRepository.getRecruiterByCompanyId(ArgumentMatchers.anyLong(), pageable)).thenReturn(recruiterPage);
//        recruiterService.getRecruiterByCompanyId(1,10,1234L);
    }

    @Test
    void searchRecruitersForAdminTest(){

    }

    @Test
    void uploadLicenseTest(){

    }

    @Test
    void searchLicenseApprovalForAdminTest(){
        List<Recruiter> recruiter = new ArrayList<>();
        for (Recruiter r: recruiter) {
            r.setId(1L);
            r.setUserId(123L);
            r.setBusinessLicenseApprovalStatus("Pending");
            r.setAdditionalLicenseApprovalStatus("Pending");
            r.setCompanyId(1234L);
        }
        Mockito.when(recruiterRepository.searchLicenseApprovalForAdmin(ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString())).thenReturn(recruiter);
        List<Recruiter> result = recruiterService.searchLicenseApprovalForAdmin("Pending", "Pending");
        assertEquals(0, result.size());

    }

    @Test
    void approveLicenseTest(){

    }

    @Test
    void getByIdTest(){
        Recruiter recruiter = new Recruiter();
        recruiter.setId(1L);
        recruiter.setUserId(123L);
        Mockito.when(recruiterRepository.getById(ArgumentMatchers.anyLong())).thenReturn(recruiter);
        Recruiter r = recruiterService.getById(1L);
        assertEquals(1L, r.getId());
    }

    @Test
    void updateTotalCvViewTest(){
        long total = 1L;
        Recruiter recruiter = new Recruiter();
        recruiter.setId(1L);
        recruiterService.updateTotalCvView(total,recruiter.getId());
    }

    @Test
    void insertGoogleRecruiterTest(){
        GooglePojo pojo = new GooglePojo();
        pojo.setEmail("namnh@gmail.com");
        pojo.setName("Nguyen Hoai Nam");
        pojo.setPicture("");
        Recruiter recruiter = new Recruiter();
        recruiter.setFullName(pojo.getName());
        recruiter.setAvatarUrl(pojo.getPicture());
        recruiter.setUserId(1L);
        Users users = new Users();
        users.setId(1L);
        recruiterService.insertGoogleRecruiter(pojo, users);
    }

    @Test
    void getTotalViewCVTest(){
        Recruiter recruiter = new Recruiter();
        recruiter.setId(1L);
        recruiter.setTotalCvView(100);
        Mockito.when(recruiterRepository.getTotalViewCV(ArgumentMatchers.anyLong())).thenReturn(100);
        assertEquals(100, recruiterService.getTotalViewCV(1L));
    }
}
