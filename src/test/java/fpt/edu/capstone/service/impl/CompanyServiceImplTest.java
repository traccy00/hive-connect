package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.Company;
import fpt.edu.capstone.repository.CompanyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceImplTest {

    @Mock
    private CompanyRepository mockCompanyRepository;

    private CompanyServiceImpl companyServiceImplUnderTest;

    @Before
    public void setUp() throws Exception {
        companyServiceImplUnderTest = new CompanyServiceImpl(mockCompanyRepository);
    }

    @Test
    public void testGetCompanyById() {
        final Company company = new Company();
        company.setId(0L);
        company.setFieldWork("fieldWork");
        company.setName("name");
        company.setEmail("email");
        company.setPhone("phone");
        company.setDescription("description");
        company.setWebsite("website");
        company.setNumberEmployees("numberEmployees");
        company.setAddress("address");
        company.setTaxCode("taxCode");
        company.setIsDeleted(0);
        company.setMapUrl("mapUrl");
        company.setCreatorId(0L);
        company.setLocked(false);
        when(mockCompanyRepository.getCompanyById(0L)).thenReturn(company);

        // Run the test
        final Company result = companyServiceImplUnderTest.getCompanyById(0L);

        // Verify the results
    }

    @Test
    public void testExistById() {
        // Setup
        when(mockCompanyRepository.existsById(0L)).thenReturn(false);

        // Run the test
        final boolean result = companyServiceImplUnderTest.existById(0L);

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    public void testGetAllCompany() {
        // Setup
        // Configure CompanyRepository.findAll(...).
        final Company company = new Company();
        company.setId(0L);
        company.setFieldWork("fieldWork");
        company.setName("name");
        company.setEmail("email");
        company.setPhone("phone");
        company.setDescription("description");
        company.setWebsite("website");
        company.setNumberEmployees("numberEmployees");
        company.setAddress("address");
        company.setTaxCode("taxCode");
        company.setIsDeleted(0);
        company.setMapUrl("mapUrl");
        company.setCreatorId(0L);
        company.setLocked(false);
        final List<Company> companies = Arrays.asList(company);
        when(mockCompanyRepository.findAll()).thenReturn(companies);

        // Run the test
        final List<Company> result = companyServiceImplUnderTest.getAllCompany();

        // Verify the results
    }

    @Test
    public void testGetAllCompany_CompanyRepositoryReturnsNoItems() {
        // Setup
        when(mockCompanyRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<Company> result = companyServiceImplUnderTest.getAllCompany();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testFindById() {
        // Setup
        // Configure CompanyRepository.findById(...).
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
        when(mockCompanyRepository.findById(0L)).thenReturn(company);

        // Run the test
        final Optional<Company> result = companyServiceImplUnderTest.findById(0L);

        // Verify the results
    }

    @Test
    public void testFindById_CompanyRepositoryReturnsAbsent() {
        // Setup
        when(mockCompanyRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final Optional<Company> result = companyServiceImplUnderTest.findById(0L);

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    public void testSearchCompany() {
        // Setup
        // Configure CompanyRepository.getAllByName(...).
        final Company company = new Company();
        company.setId(0L);
        company.setFieldWork("fieldWork");
        company.setName("name");
        company.setEmail("email");
        company.setPhone("phone");
        company.setDescription("description");
        company.setWebsite("website");
        company.setNumberEmployees("numberEmployees");
        company.setAddress("address");
        company.setTaxCode("taxCode");
        company.setIsDeleted(0);
        company.setMapUrl("mapUrl");
        company.setCreatorId(0L);
        company.setLocked(false);
        final Page<Company> companies = new PageImpl<>(Arrays.asList(company));
        when(mockCompanyRepository.getAllByName(any(Pageable.class), eq("companyName"))).thenReturn(companies);

        // Run the test
        final Page<Company> result = companyServiceImplUnderTest.searchCompany(PageRequest.of(0, 1), "companyName");

        // Verify the results
    }

    @Test
    public void testSearchCompany_CompanyRepositoryReturnsNoItems() {
        // Setup
        when(mockCompanyRepository.getAllByName(any(Pageable.class), eq("companyName")))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        // Run the test
        final Page<Company> result = companyServiceImplUnderTest.searchCompany(PageRequest.of(0, 1), "companyName");

        // Verify the results
    }
}
