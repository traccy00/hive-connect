package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.company.CreateCompanyRequest;
import fpt.edu.capstone.entity.Company;

import java.util.List;

public interface CompanyService {

    Company getCompanyById(long id);

    boolean existById(long id);

    List <Company> getAllCompany();

    Company createCompany(CreateCompanyRequest request);
}
