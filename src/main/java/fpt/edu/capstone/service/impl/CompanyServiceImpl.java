package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.company.CreateCompanyRequest;
import fpt.edu.capstone.entity.Company;
import fpt.edu.capstone.entity.Fields;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.CompanyRepository;
import fpt.edu.capstone.service.CompanyService;
import fpt.edu.capstone.service.FieldsService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final ModelMapper modelMapper;

    private final CompanyRepository companyRepository;

    private final FieldsService fieldsService;

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
    public Company createCompany(CreateCompanyRequest request) {
        Optional<Fields> field = fieldsService.findById(request.getFieldWork());
        if(!field.isPresent()) {
            throw new HiveConnectException("Field is not exist");
        }
        if (StringUtils.isBlank(request.getName())
                || StringUtils.isBlank(request.getEmail())
                || StringUtils.isBlank(request.getPhoneNumber())
                || StringUtils.isBlank(request.getTaxCode())
                || StringUtils.isBlank(request.getAddress())
                || StringUtils.isBlank(request.getNumberEmployees())) {
            throw new HiveConnectException("Please input to mandatory fields");
        }
        Company company = modelMapper.map(request, Company.class);
        companyRepository.save(company);
        Company savedCompany = companyRepository.getCompanyById(company.getId());
        return savedCompany;
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
}
