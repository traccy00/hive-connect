package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.admin.CommonRecruiterInformationResponse;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
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
import org.springframework.web.multipart.MultipartFile;

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
                Users user = userService.getUserById(recruiter.getUserId());
                Image image = imageService.getAvatarRecruiter(recruiter.getId());
                RecruiterBaseOnCompanyResponse response =
                        new RecruiterBaseOnCompanyResponse(recruiter.getId(), recruiter.getFullName(), image.getUrl(), recruiter.getFullName(),
                                recruiter.isGender(), recruiter.getPosition(), recruiter.getLinkedinAccount(), user.getEmail(), user.getPhone());
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
    public RecruiterProfileResponse updateRecruiterInformation(long recruiterId, RecruiterUpdateProfileRequest request, MultipartFile multipartFile) throws Exception {
        Recruiter recruiter = recruiterService.getById(recruiterId);
        if (recruiter == null) {
            throw new HiveConnectException("Người dùng không tồn tại");
        }
        if (request == null && multipartFile == null) {
            throw new HiveConnectException("Không có dữ liệu thay đổi, không thể cập nhật");
        }
//        LocalDateTime nowDate = LocalDateTime.now();
        if (multipartFile != null) {
            Image image = imageService.getAvatarRecruiter(recruiterId);
            if (image == null) {
                image = new Image();
                image.create();
            } else {
                image.update();
            }
            String avatarName = amazonS3ClientService.uploadFileAmazonS3(null, multipartFile);
            image.setName(avatarName);
            image.setUrl(ResponseMessageConstants.AMAZON_SAVE_URL + avatarName);
            image.setAvatar(true);
            image.setRecruiterId(recruiterId);
            imageRepository.save(image);

//            profileResponse.setRecruiterId(recruiterId);
//            profileResponse.setAvatarName(avatarName);
//            profileResponse.setAvatarUrl(ResponseMessageConstants.AMAZON_SAVE_URL + avatarName);
//            profileResponse.setCompanyId(recruiter.getCompanyId());
//            Company company = companyService.getCompanyById(recruiter.getCompanyId());
//            if (company != null) {
//                profileResponse.setCompanyId(company.getId());
//                profileResponse.setCompanyName(company.getName());
//                profileResponse.setCompanyAddress(company.getAddress());
//            }
//            profileResponse.setFullName(recruiter.getFullName());
//            profileResponse.setGender(recruiter.isGender());
//            profileResponse.setPosition(recruiter.getPosition());
//            profileResponse.setLinkedinAccount(recruiter.getLinkedinAccount());
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

//            profileResponse.setEmail(user.getEmail());
//            profileResponse.setPhone(user.getPhone());
//            profileResponse.setUserName(user.getUsername());
        }
        RecruiterProfileResponse profileResponse = getRecruiterProfile(recruiterId);
        return profileResponse;
    }

    @Override
    public RecruiterProfileResponse getRecruiterProfile(long recruiterId) {
        RecruiterProfileResponse profileResponse = new RecruiterProfileResponse();
        Recruiter recruiter = recruiterRepository.getById(recruiterId);
        if (recruiter == null) {
            throw new HiveConnectException("Recruiter doesn't exist");
        }

        profileResponse.setRecruiterId(recruiter.getId());

        Users user = userService.getUserById(recruiter.getUserId());
        //recruiter tồn tại nhưng user không tồn tại (database lỗi)
        if (user == null) {
            throw new HiveConnectException("Liên hệ admin");
        }
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
}
