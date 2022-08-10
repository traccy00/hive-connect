package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.Company;
import fpt.edu.capstone.entity.RequestJoinCompany;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.RequestJoinCompanyRepository;
import fpt.edu.capstone.service.CompanyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestJoinCompanyServiceImplTest {

    @Mock
    private RequestJoinCompanyRepository mockRequestJoinCompanyRepository;
    @Mock
    private CompanyService mockCompanyService;

    private RequestJoinCompanyServiceImpl requestJoinCompanyServiceImplUnderTest;

    @Before
    public void setUp() {
        requestJoinCompanyServiceImplUnderTest = new RequestJoinCompanyServiceImpl(mockRequestJoinCompanyRepository,
                mockCompanyService);
    }

    private RequestJoinCompany requestJoinCompany(){
        RequestJoinCompany requestJoinCompany = new RequestJoinCompany();
        requestJoinCompany.setId(0L);
        requestJoinCompany.setStatus("Pending");
        requestJoinCompany.setSenderId(0L);
        requestJoinCompany.setCompanyId(0L);
        requestJoinCompany.setApproverId(0L);
        return requestJoinCompany;
    }

    private Company company(){
        Company company1 = new Company();
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
        return company1;
    }
    @Test
    public void testCreateRequest() {
        final RequestJoinCompany requestJoinCompany = requestJoinCompany();
        final Company company1 = company();
        final Optional<Company> company = Optional.of(company1);
        when(mockCompanyService.findById(0L)).thenReturn(company);
        final RequestJoinCompany requestJoinCompany1 = requestJoinCompany();
        when(mockRequestJoinCompanyRepository.save(any(RequestJoinCompany.class))).thenReturn(requestJoinCompany1);
        final RequestJoinCompany result = requestJoinCompanyServiceImplUnderTest.createRequest(requestJoinCompany);
    }

    @Test
    public void testCreateRequest_CompanyServiceReturnsAbsent() {
        final RequestJoinCompany requestJoinCompany = requestJoinCompany();
        when(mockCompanyService.findById(0L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> requestJoinCompanyServiceImplUnderTest.createRequest(requestJoinCompany))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testGetSentRequest() {
        final RequestJoinCompany requestJoinCompany = requestJoinCompany();
        final Optional<RequestJoinCompany> optional = Optional.of(requestJoinCompany);
        when(mockRequestJoinCompanyRepository.findBySenderId(0L)).thenReturn(optional);
        final Optional<RequestJoinCompany> result = requestJoinCompanyServiceImplUnderTest.getSentRequest(0L);
    }

    @Test
    public void testGetSentRequest_RequestJoinCompanyRepositoryReturnsAbsent() {
        when(mockRequestJoinCompanyRepository.findBySenderId(0L)).thenReturn(Optional.empty());
        final Optional<RequestJoinCompany> result = requestJoinCompanyServiceImplUnderTest.getSentRequest(0L);
        assertThat(result).isEmpty();
    }

    @Test
    public void testGetReceiveRequest() {
        final RequestJoinCompany requestJoinCompany = requestJoinCompany();
        final Optional<List<RequestJoinCompany>> requestJoinCompanies = Optional.of(Arrays.asList(requestJoinCompany));
        when(mockRequestJoinCompanyRepository.findByApproverId(0L)).thenReturn(requestJoinCompanies);
        final Optional<List<RequestJoinCompany>> result = requestJoinCompanyServiceImplUnderTest.getReceiveRequest(0L);
    }

    @Test
    public void testGetReceiveRequest_RequestJoinCompanyRepositoryReturnsAbsent() {
        when(mockRequestJoinCompanyRepository.findByApproverId(0L)).thenReturn(Optional.empty());
        final Optional<List<RequestJoinCompany>> result = requestJoinCompanyServiceImplUnderTest.getReceiveRequest(0L);
        assertThat(result).isEmpty();
    }

    @Test
    public void testGetReceiveRequest_RequestJoinCompanyRepositoryReturnsNoItems() {
        when(mockRequestJoinCompanyRepository.findByApproverId(0L)).thenReturn(Optional.of(Collections.emptyList()));
        final Optional<List<RequestJoinCompany>> result = requestJoinCompanyServiceImplUnderTest.getReceiveRequest(0L);
        assertThat(result).isEqualTo(Optional.of(Collections.emptyList()));
    }

    @Test
    public void testApproveRequest() {
        requestJoinCompanyServiceImplUnderTest.approveRequest("status", 0L);
        verify(mockRequestJoinCompanyRepository).approveRequest("status", 0L);
    }

    @Test
    public void testFindById() {
        final RequestJoinCompany requestJoinCompany = requestJoinCompany();
        final Optional<RequestJoinCompany> optional = Optional.of(requestJoinCompany);
        when(mockRequestJoinCompanyRepository.findById(0L)).thenReturn(optional);
        final Optional<RequestJoinCompany> result = requestJoinCompanyServiceImplUnderTest.findById(0L);
    }

    @Test
    public void testFindById_RequestJoinCompanyRepositoryReturnsAbsent() {
        when(mockRequestJoinCompanyRepository.findById(0L)).thenReturn(Optional.empty());
        final Optional<RequestJoinCompany> result = requestJoinCompanyServiceImplUnderTest.findById(0L);
        assertThat(result).isEmpty();
    }
}
