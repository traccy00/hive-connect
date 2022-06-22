package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.Company;
import fpt.edu.capstone.repository.CompanyRepository;
import fpt.edu.capstone.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
