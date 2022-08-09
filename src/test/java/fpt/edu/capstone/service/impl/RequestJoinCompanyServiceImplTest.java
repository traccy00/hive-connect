package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.company.CreateCompanyRequest;
import fpt.edu.capstone.entity.Company;
import fpt.edu.capstone.entity.RequestJoinCompany;
import fpt.edu.capstone.repository.CompanyRepository;
import fpt.edu.capstone.repository.RequestJoinCompanyRepository;
import fpt.edu.capstone.service.CompanyManageService;
import fpt.edu.capstone.service.CompanyService;
import org.checkerframework.checker.units.qual.C;
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
public class RequestJoinCompanyServiceImplTest {
    @InjectMocks
    RequestJoinCompanyServiceImpl requestJoinCompanyService;

    @Mock
    RequestJoinCompanyRepository requestJoinCompanyRepository;

    @Mock
    CompanyManageService companyManageService;

    @Mock
    CompanyRepository companyRepository;

    @Test
    void createRequestTest(){
        RequestJoinCompany company = new RequestJoinCompany();
        company.setId(1L);
        company.setStatus("Pending");
        company.setCompanyId(123L);

        CreateCompanyRequest c = new CreateCompanyRequest();
        c.setName("FPT Software");

        Company com = new Company();
        com.setId(1L);
        com.setName(c.getName());
        Mockito.when(companyRepository.save(ArgumentMatchers.any(Company.class))).thenReturn(com);
        Company saveCompany = companyManageService.createCompany(c);
        Mockito.when(requestJoinCompanyRepository.save(ArgumentMatchers.any(RequestJoinCompany.class))).thenReturn(company);
        RequestJoinCompany requestJoinCompany =  requestJoinCompanyService.createRequest(company);
        assertEquals(1L, requestJoinCompany.getId());
    }

    @Test
    void getSentRequestTest(){
        RequestJoinCompany company = new RequestJoinCompany();
        company.setId(1L);
        company.setCompanyId(123L);
        company.setSenderId(1L);
        Mockito.when(requestJoinCompanyRepository.findBySenderId(ArgumentMatchers.anyLong())).thenReturn(Optional.of(company));
        Optional<RequestJoinCompany> optional = requestJoinCompanyService.getSentRequest(1L);
        assertEquals(1L, optional.get().getSenderId());
    }

    @Test
    void getReceiveRequestTest(){
        List<RequestJoinCompany> company = new ArrayList<>();
        for (RequestJoinCompany c: company) {
            c.setId(1L);
            c.setCompanyId(123L);
            c.setSenderId(1L);
            c.setApproverId(12L);
            company.add(c);
        }
        Mockito.when(requestJoinCompanyRepository.findByApproverId(ArgumentMatchers.anyLong())).thenReturn(Optional.of(company));
        Optional<List<RequestJoinCompany>> optional = requestJoinCompanyService.getReceiveRequest(12L);
        assertEquals(0, optional.get().size());
    }
    @Test
    void approveRequestTest(){
        String status= "Pending";
        long id = 1L;
        requestJoinCompanyService.approveRequest(status,id);
    }

    @Test
    void findById(){
        RequestJoinCompany company = new RequestJoinCompany();
        company.setId(1L);
        company.setCompanyId(123L);
        company.setSenderId(1L);
        Mockito.when(requestJoinCompanyRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(company));
        Optional <RequestJoinCompany> optional = requestJoinCompanyService.findById(1L);
        assertEquals(1L, optional.get().getId());
    }
}
