package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.company.CreateCompanyRequest;
import fpt.edu.capstone.entity.Company;
import fpt.edu.capstone.entity.Image;
import fpt.edu.capstone.utils.ResponseDataPagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    Company getCompanyById(long id);

    boolean existById(long id);

    List <Company> getAllCompany();

    Company insertCompany(Company newCompany);

    Optional<Company> getCompanyByName(String companyName);

    Optional<Company> getCompanyByTaxCode(String taxCode);

    Optional<Company> findById(long companyId);

    void updateCompanyAvatarUrl(String avatarId, long companyId);

    Page<Company> searchCompany(Pageable pageable, String companyName);

}
