package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.company.CompanyResponse;
import fpt.edu.capstone.dto.company.TopCompanyResponse;
import fpt.edu.capstone.entity.Image;
import fpt.edu.capstone.service.AppliedJobService;
import fpt.edu.capstone.service.CompanyManageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyManageServiceImpl implements CompanyManageService {

    private final AppliedJobService appliedJobService;

    private final ImageService imageService;

    @Override
    public List<TopCompanyResponse> getTop12Companies() {
        List<TopCompanyResponse> responseList = new ArrayList<>();
        List<CompanyResponse> companyList = appliedJobService.getTop12Companies();
        for (CompanyResponse company : companyList) {
            TopCompanyResponse response = new TopCompanyResponse();
            Optional<Image> image = imageService.findAvatarByCompanyId(company.getCompanyId());
            if (image.isPresent()) {
                response.setCompanyAvatar(image.get().getName());
            }
            response.setApplyCvNumber(company.getApplyCvNumber());
            response.setCompanyId(company.getCompanyId());
            response.setCompanyName(company.getCompanyName());
            responseList.add(response);
        }
        return responseList;
    }
}
