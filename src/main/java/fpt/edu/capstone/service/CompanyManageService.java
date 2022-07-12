package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.company.TopCompanyResponse;

import java.util.List;

public interface CompanyManageService {
    List<TopCompanyResponse> getTop12Companies();
}
