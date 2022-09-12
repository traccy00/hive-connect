package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.recruiter.ReceiveRequestJoinCompanyResponse;
import fpt.edu.capstone.entity.Company;
import fpt.edu.capstone.entity.RequestJoinCompany;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.RequestJoinCompanyRepository;
import fpt.edu.capstone.service.CompanyService;
import fpt.edu.capstone.utils.ResponseDataPagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestJoinCompanyServiceImplTest {

    @Mock
    private RequestJoinCompanyRepository mockRequestJoinCompanyRepository;
    @Mock
    private CompanyService mockCompanyService;

    private RequestJoinCompanyServiceImpl requestJoinCompanyServiceImplUnderTest;

    @BeforeEach
    void setUp() throws Exception {
        requestJoinCompanyServiceImplUnderTest = new RequestJoinCompanyServiceImpl(mockRequestJoinCompanyRepository,
                mockCompanyService);
    }

    @Test
    void testCreateRequest() {
        final RequestJoinCompany requestJoinCompany = new RequestJoinCompany();
        requestJoinCompany.setId(0L);
        requestJoinCompany.setStatus("Pending");
        requestJoinCompany.setSenderId(0L);
        requestJoinCompany.setCompanyId(0L);
        requestJoinCompany.setApproverId(0L);

        final Company company1 = new Company();
        company1.setId(0L);
        company1.setFieldWork("fieldWork");
        company1.setName("name");
        company1.setEmail("email");
        company1.setPhone("phone");
        company1.setDescription("description");
        company1.setWebsite("website");
        company1.setNumberEmployees("numberEmployees");
        company1.setAddress("address");
        company1.setTaxCode("taxCode");
        company1.setIsDeleted(0);
        company1.setMapUrl("mapUrl");
        company1.setCreatorId(0L);
        company1.setLocked(false);
        final Optional<Company> company = Optional.of(company1);
        when(mockCompanyService.findById(0L)).thenReturn(company);

        final RequestJoinCompany requestJoinCompany1 = new RequestJoinCompany();
        requestJoinCompany1.setId(0L);
        requestJoinCompany1.setStatus("Pending");
        requestJoinCompany1.setSenderId(0L);
        requestJoinCompany1.setCompanyId(0L);
        requestJoinCompany1.setApproverId(0L);
        when(mockRequestJoinCompanyRepository.save(any(RequestJoinCompany.class))).thenReturn(requestJoinCompany1);
        final RequestJoinCompany result = requestJoinCompanyServiceImplUnderTest.createRequest(requestJoinCompany);
    }

    @Test
    void testCreateRequest_CompanyServiceReturnsAbsent() {
        final RequestJoinCompany requestJoinCompany = new RequestJoinCompany();
        requestJoinCompany.setId(0L);
        requestJoinCompany.setStatus("Pending");
        requestJoinCompany.setSenderId(0L);
        requestJoinCompany.setCompanyId(0L);
        requestJoinCompany.setApproverId(0L);
        when(mockCompanyService.findById(0L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> requestJoinCompanyServiceImplUnderTest.createRequest(requestJoinCompany))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    void testGetSentRequest() {
        final RequestJoinCompany requestJoinCompany1 = new RequestJoinCompany();
        requestJoinCompany1.setId(0L);
        requestJoinCompany1.setStatus("Pending");
        requestJoinCompany1.setSenderId(0L);
        requestJoinCompany1.setCompanyId(0L);
        requestJoinCompany1.setApproverId(0L);
        final Optional<RequestJoinCompany> requestJoinCompany = Optional.of(requestJoinCompany1);
        when(mockRequestJoinCompanyRepository.findBySenderId(0L)).thenReturn(requestJoinCompany);
        final Optional<RequestJoinCompany> result = requestJoinCompanyServiceImplUnderTest.getSentRequest(0L);
    }

    @Test
    void testGetSentRequest_RequestJoinCompanyRepositoryReturnsAbsent() {
        when(mockRequestJoinCompanyRepository.findBySenderId(0L)).thenReturn(Optional.empty());
        final Optional<RequestJoinCompany> result = requestJoinCompanyServiceImplUnderTest.getSentRequest(0L);
        assertThat(result).isEmpty();
    }

    @Test
    void testGetReceiveRequest() {
        final RequestJoinCompany requestJoinCompany = new RequestJoinCompany();
        requestJoinCompany.setId(0L);
        requestJoinCompany.setStatus("Pending");
        requestJoinCompany.setSenderId(0L);
        requestJoinCompany.setCompanyId(0L);
        requestJoinCompany.setApproverId(0L);
        final Page<RequestJoinCompany> requestJoinCompanies = new PageImpl<>(Arrays.asList(requestJoinCompany));
        when(mockRequestJoinCompanyRepository.findByApproverId(any(Pageable.class), eq(0L)))
                .thenReturn(requestJoinCompanies);
        final ResponseDataPagination result = requestJoinCompanyServiceImplUnderTest.getReceiveRequest(1, 10, 0L);
    }

    @Test
    void testGetReceiveRequest_RequestJoinCompanyRepositoryReturnsNoItems() {
        when(mockRequestJoinCompanyRepository.findByApproverId(any(Pageable.class), eq(0L)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));
        final ResponseDataPagination result = requestJoinCompanyServiceImplUnderTest.getReceiveRequest(1, 10, 0L);
    }

    @Test
    void testApproveRequest() {
        requestJoinCompanyServiceImplUnderTest.approveRequest("status", 0L);
        verify(mockRequestJoinCompanyRepository).approveRequest("status", 0L);
    }

    @Test
    void testFindById() throws Exception {
        final RequestJoinCompany requestJoinCompany1 = new RequestJoinCompany();
        requestJoinCompany1.setId(0L);
        requestJoinCompany1.setStatus("Pending");
        requestJoinCompany1.setSenderId(0L);
        requestJoinCompany1.setCompanyId(0L);
        requestJoinCompany1.setApproverId(0L);
        final Optional<RequestJoinCompany> requestJoinCompany = Optional.of(requestJoinCompany1);
        when(mockRequestJoinCompanyRepository.findById(0L)).thenReturn(requestJoinCompany);
        final Optional<RequestJoinCompany> result = requestJoinCompanyServiceImplUnderTest.findById(0L);
    }

    @Test
    void testFindById_RequestJoinCompanyRepositoryReturnsAbsent() {
        when(mockRequestJoinCompanyRepository.findById(0L)).thenReturn(Optional.empty());
        final Optional<RequestJoinCompany> result = requestJoinCompanyServiceImplUnderTest.findById(0L);
        assertThat(result).isEmpty();
    }

    @Test
    void testGetReceiveRequestJoinCompanyWithFilter() {
        when(mockRequestJoinCompanyRepository.getReceiveRequestJoinCompanyWithFilter(eq("fullName"), eq("email"),
                eq("phone"), eq("status"), eq(0L), any(Pageable.class))).thenReturn(new PageImpl<>(Arrays.asList()));
        final Page<ReceiveRequestJoinCompanyResponse> result = requestJoinCompanyServiceImplUnderTest.getReceiveRequestJoinCompanyWithFilter(
                "fullName", "email", "phone", "status", 0L, 10, 1);
    }

    @Test
    void testGetReceiveRequestJoinCompanyWithFilter_RequestJoinCompanyRepositoryReturnsNoItems() {
        when(mockRequestJoinCompanyRepository.getReceiveRequestJoinCompanyWithFilter(eq("fullName"), eq("email"),
                eq("phone"), eq("status"), eq(0L), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));
        final Page<ReceiveRequestJoinCompanyResponse> result = requestJoinCompanyServiceImplUnderTest.getReceiveRequestJoinCompanyWithFilter(
                "fullName", "email", "phone", "status", 0L, 10, 1);
    }

    @Test
    void testDeleteOldSentRequest() {
        requestJoinCompanyServiceImplUnderTest.deleteOldSentRequest(0L);
        verify(mockRequestJoinCompanyRepository).deleteById(0L);
    }
}
