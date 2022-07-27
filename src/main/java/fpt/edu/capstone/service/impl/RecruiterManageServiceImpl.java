package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.admin.CommonRecruiterInformationResponse;
import fpt.edu.capstone.dto.company.CompanyInformationResponse;
import fpt.edu.capstone.dto.recruiter.*;
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
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        //verify step 1
        if (user.isVerifiedEmail()) {
            step++;
        }
        //verify step 2
        if (user.isVerifiedPhone()) {
            step++;
        }
        //verify step 3
        if (optionalRecruiter.get().getBusinessLicenseApprovalStatus() != null
                && optionalRecruiter.get().getBusinessLicenseApprovalStatus().equals(Enums.ApprovalStatus.APPROVED.getStatus())) {
            step++;
        }
        response.setRecruiterFullName(optionalRecruiter.get().getFullName());
        response.setVerifyStep(step + " / " + totalStep);

        CountTotalCreatedJobResponse countTotalCreatedJobResponse = jobService.countTotalCreatedJobOfRecruiter(recruiterId);
        long totalCreatedJob = 0;
        if (countTotalCreatedJobResponse != null) {
            totalCreatedJob = countTotalCreatedJobResponse.getTotalCreatedJob();
        }
        response.setTotalCreatedJob(totalCreatedJob);

        CountCandidateApplyPercentageResponse countApplyCandidate = appliedJobService.countApplyPercentage(recruiterId);
        String result = "0";
        if (countApplyCandidate != null) {
            long totalApply = countApplyCandidate.getTotalApplied();
            long numberRecruits = countApplyCandidate.getNumberRecruits();
            double applyPercentage = (double) totalApply / numberRecruits;
            DecimalFormat formatter = new DecimalFormat("##.##"); //
            formatter.setRoundingMode(RoundingMode.DOWN); // Towards zero
            result = formatter.format(applyPercentage);
        }
        response.setCandidateApplyPercentage(result);

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
        if ((request.getFullName() == null || request.getFullName().trim().isEmpty())
                || (request.getPhone() == null || request.getPhone().trim().isEmpty())
                || (request.getPosition() == null || request.getPosition().trim().isEmpty())
                || (request.getLinkedinAccount() == null || request.getLinkedinAccount().trim().isEmpty())) {
            throw new HiveConnectException("Vui lòng nhập thông tin bắt buộc.");
        }
        recruiter.setFullName(request.getFullName());
        recruiter.setPosition(request.getPhone());
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
        if (!companyOp.isPresent()) {
            throw new HiveConnectException("Thông tin công ty không tồn tại");
        }
        Company tmp = companyOp.get();
        response.setAddress(tmp.getAddress());
        Image image = imageService.getImageCompany(companyId, true);
        if (image != null) {
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
        if (recruiterId != 0 && creatorId == recruiterId) {
            isCreator = true;
        }
        response.setCreator(isCreator);
        List<Image> companyImageList = imageService.getCompanyImageList(companyId, false, false);
        List<String> companyImageUrls = companyImageList.stream().map(Image::getUrl).collect(Collectors.toList());
        response.setCompanyImageUrlList(companyImageUrls);
        List<Image> coverImage = imageService.getCompanyImageList(companyId, false, true);
        if (coverImage != null && !coverImage.isEmpty()) {
            response.setCoverImageUrl(coverImage.get(0).getUrl());
        }
        return response;
    }
}
