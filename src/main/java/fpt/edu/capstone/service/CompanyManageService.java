package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.company.TopCompanyResponse;
import fpt.edu.capstone.dto.company.UpdateCompanyInforResponse;

import java.util.List;

public interface CompanyManageService {
    List<TopCompanyResponse> getTop12Companies();

    UpdateCompanyInforResponse updateCompanyInformation(long recruiterId, UpdateCompanyInforResponse request);
}
