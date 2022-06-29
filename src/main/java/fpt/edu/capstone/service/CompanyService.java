package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.company.CreateCompanyRequest;
import fpt.edu.capstone.entity.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    Company getCompanyById(long id);

    boolean existById(long id);

    List <Company> getAllCompany();

    Company insertCompany(Company newCompany);

    Optional<Company> getCompanyByName(String companyName);

    Optional<Company> getCompanyByTaxCode(String taxCode);

    Company createCompany(CreateCompanyRequest request);
}
