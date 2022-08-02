package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.company.CreateCompanyRequest;
import fpt.edu.capstone.entity.Company;
import fpt.edu.capstone.entity.Fields;
import fpt.edu.capstone.entity.Image;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.CompanyRepository;
import fpt.edu.capstone.repository.ImageRepository;
import fpt.edu.capstone.service.CompanyService;
import fpt.edu.capstone.service.FieldsService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseDataPagination;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public Company getCompanyById(long id) {
        return companyRepository.getCompanyById(id);
    }

    @Override
    public boolean existById(long id) {
        return companyRepository.existsById(id);
    }

    @Override
    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }

    @Override
    public Optional<Company> findById(long companyId) {
        return companyRepository.findById(companyId);
    }

    @Override
    public void updateCompanyAvatarUrl(String avatarId, long companyId) {
        companyRepository.updateCompanyAvatarUrl(avatarId, companyId);
    }

    @Override
    public Company insertCompany(Company newCompany) {
        return companyRepository.save(newCompany);
    }

    @Override
    public Optional<Company> getCompanyByName(String companyName) {
        return companyRepository.getCompanyByName(companyName);
    }

    @Override
    public Optional<Company> getCompanyByTaxCode(String taxCode) {
        return companyRepository.getCompanyByTaxcode(taxCode);
    }

    @Override
    public Page<Company> searchCompany(Pageable pageable, String companyName) {
        return companyRepository.getAllByName(pageable, companyName);
    }
}
