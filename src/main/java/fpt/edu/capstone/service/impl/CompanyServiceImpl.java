package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.company.CreateCompanyRequest;
import fpt.edu.capstone.entity.Company;
import fpt.edu.capstone.entity.Fields;
import fpt.edu.capstone.entity.Image;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.CompanyRepository;
import fpt.edu.capstone.repository.ImageRepository;
import fpt.edu.capstone.service.CompanyService;
import fpt.edu.capstone.service.FieldsService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final ModelMapper modelMapper;

    private final CompanyRepository companyRepository;

    private final FieldsService fieldsService;

    private final ImageRepository imageRepository;

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
    @Transactional(rollbackOn = Exception.class)
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

        Image image = new Image();
        image.setCompanyId(company.getId());
        image.setAvatar(true);
        imageRepository.save(image);
        Company savedCompany = companyRepository.getCompanyById(company.getId());
        return savedCompany;
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
    public List<Company> searchCompany(String companyName) {
        return companyRepository.getAllByName(companyName);
    }
}
