package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.CV.FindCVResponse;
import fpt.edu.capstone.dto.CV.IFindCVResponse;
import fpt.edu.capstone.dto.admin.CommonRecruiterInformationResponse;
import fpt.edu.capstone.dto.company.CompanyInformationResponse;
import fpt.edu.capstone.dto.recruiter.*;
import fpt.edu.capstone.entity.*;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.*;
import fpt.edu.capstone.service.*;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseDataPagination;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    private final ImageServiceImpl imageService;

    private final CompanyService companyService;

    private final CVService cvService;

    private final CandidateService candidateService;

    private final WorkExperienceService workExperienceService;

    private final EducationService educationService;

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
        if (request.getAvatarUrl() != null && request.getAvatarUrl().trim().isEmpty()) {
            recruiter.setAvatarUrl(request.getAvatarUrl());
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
        if(recruiter.getCompanyId() == 0) {
            profileResponse.setJoinedCompany(false);
        } else if (recruiter.getCompanyId() > 0) {
            profileResponse.setJoinedCompany(true);
        }
        if (recruiter.getBusinessLicenseApprovalStatus() != null
                && recruiter.getBusinessLicenseApprovalStatus().equals(Enums.ApprovalStatus.APPROVED.getStatus())) {
            profileResponse.setApprovedBusinessLicense(true);
        } else {
            profileResponse.setApprovedBusinessLicense(false);
        }
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

    @Override
    public ResponseDataPagination findCV(Integer pageNo, Integer pageSize, //int experienceOption,
                                         String candidateAddress,
                                         String techStack) {
        List<FindCVResponse> responseList = new ArrayList<>();
        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);

        Page<IFindCVResponse> cvList = cvService.findCVForRecruiter(pageable, //experienceOption,
                candidateAddress, techStack);
        for(IFindCVResponse iFindCVResponse : cvList) {
            FindCVResponse response = new FindCVResponse();
            Optional<CV> cv = cvService.findCvById(iFindCVResponse.getCvId());
            Candidate candidate = candidateService.getCandidateById(cv.get().getCandidateId());
            response.setCandidateName(candidate.getFullName());

            List<WorkExperience> workExperiences = workExperienceService.
                    getListWorkExperienceByCvId(iFindCVResponse.getCvId());
            List<String> workPositionExperiences = new ArrayList<>();
            for(WorkExperience workExperience : workExperiences) {
                workPositionExperiences.add(workExperience.getPosition() + " - " + workExperience.getCompanyName());
            }
            response.setWorkPositionExperiences(workPositionExperiences);

            List<Education> educations = educationService.getListEducationByCvId(iFindCVResponse.getCvId());
            List<String> schools = educations.stream().map(Education::getSchool).collect(Collectors.toList());
            response.setSchools(schools);

            response.setCareerGoal(candidate.getIntroduction());
            response.setCandidateAddress(candidate.getAddress());
            response.setSumExperienceYear(iFindCVResponse.getSumExperienceYear());
            responseList.add(response);
        }

        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();
        responseDataPagination.setData(responseList);
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(cvList.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(cvList.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);
        return responseDataPagination;
    }

    @Override
    public ResponseDataPagination findCVTest(Integer pageNo, Integer pageSize, int experienceOption,
                                         String candidateAddress,
                                         String techStack) {
        List<FindCVResponse> responseList = new ArrayList<>();
        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);

        Page<IFindCVResponse> cvList = cvService.findCVTest(pageable, experienceOption,
                candidateAddress, techStack);
        for(IFindCVResponse iFindCVResponse : cvList) {
            FindCVResponse response = new FindCVResponse();
            Optional<CV> cv = cvService.findCvById(iFindCVResponse.getCvId());
            Candidate candidate = candidateService.getCandidateById(cv.get().getCandidateId());
            response.setCandidateName(candidate.getFullName());

            List<WorkExperience> workExperiences = workExperienceService.
                    getListWorkExperienceByCvId(iFindCVResponse.getCvId());
            List<String> workPositionExperiences = new ArrayList<>();
            for(WorkExperience workExperience : workExperiences) {
                workPositionExperiences.add(workExperience.getPosition() + " - " + workExperience.getCompanyName());
            }
            response.setWorkPositionExperiences(workPositionExperiences);

            List<Education> educations = educationService.getListEducationByCvId(iFindCVResponse.getCvId());
            List<String> schools = educations.stream().map(Education::getSchool).collect(Collectors.toList());
            response.setSchools(schools);

            response.setCareerGoal(candidate.getIntroduction());
            response.setCandidateAddress(candidate.getAddress());
            response.setSumExperienceYear(iFindCVResponse.getSumExperienceYear());
            responseList.add(response);
        }

        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();
        responseDataPagination.setData(responseList);
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(cvList.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(cvList.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);
        return responseDataPagination;
    }
}
