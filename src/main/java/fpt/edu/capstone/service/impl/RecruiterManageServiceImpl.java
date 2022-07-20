package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.admin.CommonRecruiterInformationResponse;
import fpt.edu.capstone.dto.company.CompanyInformationResponse;
import fpt.edu.capstone.dto.recruiter.RecruiterBaseOnCompanyResponse;
import fpt.edu.capstone.dto.recruiter.RecruiterProfileResponse;
import fpt.edu.capstone.dto.recruiter.RecruiterUpdateProfileRequest;
import fpt.edu.capstone.entity.Company;
import fpt.edu.capstone.entity.Image;
import fpt.edu.capstone.entity.Recruiter;
import fpt.edu.capstone.entity.Users;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.CompanyRepository;
import fpt.edu.capstone.repository.ImageRepository;
import fpt.edu.capstone.repository.RecruiterRepository;
import fpt.edu.capstone.repository.UserRepository;
import fpt.edu.capstone.service.*;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseDataPagination;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RecruiterManageServiceImpl implements RecruiterManageService {

    private final ModelMapper modelMapper;

    private final RecruiterRepository recruiterRepository;

    private final UserRepository userRepository;

    private final JobService jobService;

    private final AppliedJobService appliedJobService;

    private final RecruiterService recruiterService;

    private final UserService userService;

    private final AmazonS3ClientService amazonS3ClientService;

    private final ImageServiceImpl imageService;

    private final ImageRepository imageRepository;

    private final CompanyService companyService;

    private final CompanyRepository companyRepository;

    @Override
    public CommonRecruiterInformationResponse getCommonInforOfRecruiter(long recruiterId) {
        CommonRecruiterInformationResponse response = new CommonRecruiterInformationResponse();
        Optional<Recruiter> optionalRecruiter = recruiterRepository.findById(recruiterId);
        if (!optionalRecruiter.isPresent()) {
            throw new HiveConnectException("Người dùng không tồn tại");
        }
        int step = 0;
        int totalStep = 3;
        Users user = userRepository.getById(optionalRecruiter.get().getUserId());
        if (user == null) {
            throw new HiveConnectException("Liên hệ admin");
        }
        if (user.isVerifiedEmail()) {
            step++;
        }
        if (user.isVerifiedPhone()) {
            step++;
        }
        if (optionalRecruiter.get().getBusinessLicenseApprovalStatus().equals(Enums.ApprovalStatus.APPROVED.getStatus())) {
            step++;
        }
        response.setRecruiterFullName(optionalRecruiter.get().getFullName());
        response.setVerifyStep(step + " / " + totalStep);
        response.setTotalCreatedJob(jobService.countTotalCreatedJobOfRecruiter(recruiterId).getTotalCreatedJob());
        response.setCandidateApplyPercentage(appliedJobService.countApplyPercentage(recruiterId).getTotalApplied()
                / appliedJobService.countApplyPercentage(recruiterId).getNumberRecruits());
        return response;
    }

    public ResponseDataPagination getRecruitersOfCompany(Integer pageNo, Integer pageSize, long companyId) {
        List<RecruiterBaseOnCompanyResponse> responseList = new ArrayList<>();
        Page<Recruiter> recruiters = recruiterService.getRecruiterByCompanyId(pageNo, pageSize, companyId);
        if (recruiters.hasContent()) {
            for (Recruiter recruiter : recruiters) {
                RecruiterBaseOnCompanyResponse response = new RecruiterBaseOnCompanyResponse();
                response.setRecruiterId(recruiter.getId());

                Users user = userService.getUserById(recruiter.getUserId());
                if (user == null) {
                    throw new HiveConnectException("Liên hệ admin");
                }
                response.setUserName(user.getUsername());

                Image image = imageService.getAvatarRecruiter(recruiter.getId());
                if (image != null) {
                    response.setAvatar(image.getUrl());
                }
                response.setFullName(recruiter.getFullName());
                response.setGender(recruiter.isGender());
                response.setPosition(recruiter.getPosition());
                response.setLinkedinAccount(recruiter.getLinkedinAccount());

                response.setEmail(user.getEmail());
                response.setPhone(user.getPhone());

                responseList.add(response);
            }
        }
        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();
        responseDataPagination.setData(responseList);
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(recruiters.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(recruiters.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);
        return responseDataPagination;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public RecruiterProfileResponse updateRecruiterInformation(long recruiterId, RecruiterUpdateProfileRequest request) throws Exception {
        Recruiter recruiter = recruiterService.getById(recruiterId);
        if (recruiter == null) {
            throw new HiveConnectException("Người dùng không tồn tại");
        }
        if (request == null) {
            throw new HiveConnectException("Không có dữ liệu thay đổi, không thể cập nhật");
        }
        if (request != null) {
            recruiter.setFullName(request.getFullName());
            recruiter.setGender(request.isGender());
            recruiter.setPosition(request.getPosition());
            recruiter.setLinkedinAccount(request.getLinkedinAccount());
            recruiterRepository.save(recruiter);

            Users user = userRepository.getUserById(recruiter.getUserId());
            //recruiter tồn tại nhưng user không tồn tại (database lỗi)
            if (user == null) {
                throw new HiveConnectException("Liên hệ admin");
            }
            user.setPhone(request.getPhone());
            userRepository.save(user);
        }
        RecruiterProfileResponse profileResponse = getRecruiterProfile(recruiter.getUserId());
        return profileResponse;
    }

    @Override
    public RecruiterProfileResponse getRecruiterProfile(long userId) {
        Users user = userService.getUserById(userId);
        //recruiter tồn tại nhưng user không tồn tại (database lỗi)
        if (user == null) {
            throw new HiveConnectException("Người dùng không tồn tại");
        }
        RecruiterProfileResponse profileResponse = new RecruiterProfileResponse();
        Recruiter recruiter = recruiterRepository.getRecruiterByUserId(userId);
        if (recruiter == null) {
            throw new HiveConnectException("Nhà tuyển dụng không tồn tại");
        }
        profileResponse.setRecruiterId(recruiter.getId());
        profileResponse.setUserName(user.getUsername());
        profileResponse.setEmail(user.getEmail());
        profileResponse.setPhone(user.getPhone());

        Image image = imageService.getAvatarRecruiter(recruiter.getId());
        if (image != null) {
            profileResponse.setAvatarName(image.getName());
            profileResponse.setAvatarUrl(image.getUrl());
        }

        Company company = companyService.getCompanyById(recruiter.getCompanyId());
        if (company != null) {
            profileResponse.setCompanyId(company.getId());
            profileResponse.setCompanyName(company.getName());
            profileResponse.setCompanyAddress(company.getAddress());
        }

        profileResponse.setFullName(recruiter.getFullName());
        profileResponse.setGender(recruiter.isGender());
        profileResponse.setPosition(recruiter.getPosition());
        profileResponse.setLinkedinAccount(recruiter.getLinkedinAccount());
        return profileResponse;
    }

    @Override
    public CompanyInformationResponse getCompanyInformation(long companyId, long recruiterId) {
        CompanyInformationResponse response = new CompanyInformationResponse();
        Optional<Company> companyOp = companyService.findById(companyId);
        if (companyOp.isPresent()) {
            Company tmp = companyOp.get();
            response.setAddress(tmp.getAddress());
            Image image = imageService.getImageCompany(companyId, true);
            if(image != null) {
                response.setAvatar(image.getUrl());
            }
            response.setDescription(tmp.getDescription());
            response.setEmail(tmp.getEmail());
            response.setFieldWork(tmp.getFieldWork());
            response.setId(tmp.getId());
            response.setName(tmp.getName());
            response.setMapUrl(tmp.getMapUrl());
            response.setPhone(tmp.getPhone());
            response.setNumberEmployees(tmp.getNumberEmployees());
            response.setWebsite(tmp.getWebsite());
            response.setTaxCode(tmp.getTaxCode());
            long creatorId = tmp.getCreatorId();
            boolean isCreator = false;
            if(recruiterId != 0 && creatorId == recruiterId) {
                isCreator = true;
            }
            response.setCreator(isCreator);
        }
        return response;
    }
}
