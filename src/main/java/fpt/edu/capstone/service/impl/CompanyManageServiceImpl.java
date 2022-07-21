package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.company.CompanyResponse;
import fpt.edu.capstone.dto.company.TopCompanyResponse;
import fpt.edu.capstone.dto.company.UpdateCompanyInforResponse;
import fpt.edu.capstone.entity.Company;
import fpt.edu.capstone.entity.Image;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.service.AppliedJobService;
import fpt.edu.capstone.service.CompanyManageService;
import fpt.edu.capstone.service.CompanyService;
import fpt.edu.capstone.service.RecruiterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyManageServiceImpl implements CompanyManageService {

    private final AppliedJobService appliedJobService;

    private final ImageServiceImpl imageService;

    private final RecruiterService recruiterService;

    private final CompanyService companyService;

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

    @Override
    @Transactional(rollbackOn = Exception.class)
    public UpdateCompanyInforResponse updateCompanyInformation(long recruiterId, UpdateCompanyInforResponse request) {
        if(!recruiterService.findById(recruiterId).isPresent()) {
            throw new HiveConnectException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
        }
        Company company = companyService.getCompanyById(request.getCompanyId());
        if(company.getCreatorId() != recruiterId) {
            throw new HiveConnectException("Không có quyền chỉnh sửa");
        }
        List<String> uploadImageUrlList = new ArrayList<>();
        //update avatar
        if(request.getAvatarUrl() != null && request.getAvatarUrl().trim().isEmpty()) {
            uploadImageUrlList.add(request.getAvatarUrl());
            imageService.saveImageCompany(true, false, company.getId(), uploadImageUrlList);
        }
        //update cover image
        if(request.getAvatarUrl() != null && request.getAvatarUrl().trim().isEmpty()) {
            uploadImageUrlList.add(request.getCoverImageUrl());
            imageService.saveImageCompany(false, true, company.getId(), uploadImageUrlList);
        }
        //xóa cái nào thì trả id của cái đó
        List<Long> deleteImageIdList = request.getDeleteImageIdList();
        imageService.deleteImagebyId(deleteImageIdList);

        //upload introduction image list of company
        uploadImageUrlList = request.getUploadImageUrlList();
        imageService.saveImageCompany(false, false, company.getId(), uploadImageUrlList);

        return null;
    }
}
