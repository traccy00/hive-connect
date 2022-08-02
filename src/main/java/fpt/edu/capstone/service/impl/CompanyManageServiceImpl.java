package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.company.CompanyResponse;
import fpt.edu.capstone.dto.company.TopCompanyResponse;
import fpt.edu.capstone.dto.company.UpdateCompanyInforRequest;
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
    public UpdateCompanyInforRequest updateCompanyInformation(long recruiterId, UpdateCompanyInforRequest request) {
        if (!recruiterService.findById(recruiterId).isPresent()) {
            throw new HiveConnectException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
        }
        Company company = companyService.getCompanyById(request.getCompanyId());
        if (company == null) {
            throw new HiveConnectException("Công ty không tồn tại");
        }
        if (company.getCreatorId() != recruiterId) {
            throw new HiveConnectException("Không có quyền chỉnh sửa");
        }
        if ((request.getCompanyEmail() == null && request.getCompanyEmail().trim().isEmpty())
                || (request.getCompanyPhone() == null && request.getCompanyPhone().trim().isEmpty())
                || (request.getCompanyDescription() == null && request.getCompanyDescription().trim().isEmpty())
                || (request.getCompanyWebsite() == null && request.getCompanyWebsite().trim().isEmpty())
                || (request.getNumberEmployees() == null && request.getNumberEmployees().trim().isEmpty())
                || (request.getCompanyAddress() == null && request.getCompanyAddress().trim().isEmpty())
                || (request.getTaxCode() == null && request.getTaxCode().trim().isEmpty())
                || (request.getMapUrl() == null && request.getMapUrl().trim().isEmpty())) {
            throw new HiveConnectException(ResponseMessageConstants.REQUIRE_INPUT_MANDATORY_FIELD);
        }
        company.setEmail(request.getCompanyEmail());
        company.setPhone(request.getCompanyPhone());
        company.setDescription(request.getCompanyDescription());
        company.setWebsite(request.getCompanyWebsite());
        company.setNumberEmployees(request.getNumberEmployees());
        company.setAddress(request.getCompanyAddress());
        company.setTaxCode(request.getTaxCode());
        company.setMapUrl(request.getMapUrl());

        List<String> uploadImageUrlList = new ArrayList<>();
        //update avatar
        if (request.getAvatarUrl() != null && request.getAvatarUrl().trim().isEmpty()) {
            uploadImageUrlList.add(request.getAvatarUrl());
            imageService.saveImageCompany(true, false, company.getId(), uploadImageUrlList);
        }
        //update cover image
        if (request.getCoverImageUrl() != null && request.getCoverImageUrl().trim().isEmpty()) {
            uploadImageUrlList.add(request.getCoverImageUrl());
            imageService.saveImageCompany(false, true, company.getId(), uploadImageUrlList);
        }
        //xóa cái nào thì trả id của cái đó
        List<Long> deleteImageIdList = request.getDeleteImageIdList();
        if(deleteImageIdList != null && !deleteImageIdList.isEmpty()) {
            imageService.deleteImageById(deleteImageIdList);
        }

        //upload introduction image list of company
        uploadImageUrlList = request.getUploadImageUrlList();
        if (uploadImageUrlList != null && !uploadImageUrlList.isEmpty()) {
            imageService.saveImageCompany(false, false, company.getId(), uploadImageUrlList);
        }
        return null;
    }
}
